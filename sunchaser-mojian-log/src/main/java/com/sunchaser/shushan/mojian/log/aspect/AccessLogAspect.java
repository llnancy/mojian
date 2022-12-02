package com.sunchaser.shushan.mojian.log.aspect;

import cn.hutool.core.util.URLUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.google.common.collect.Lists;
import com.sunchaser.shushan.mojian.base.util.JsonUtils;
import com.sunchaser.shushan.mojian.base.util.Optionals;
import com.sunchaser.shushan.mojian.base.util.ThrowableUtils;
import com.sunchaser.shushan.mojian.log.annotation.AccessLog;
import com.sunchaser.shushan.mojian.log.config.property.AccessLogProperties;
import com.sunchaser.shushan.mojian.log.entity.AccessLogBean;
import com.sunchaser.shushan.mojian.log.enums.RequestStatus;
import com.sunchaser.shushan.mojian.log.event.AccessLogEvent;
import com.sunchaser.shushan.mojian.log.util.Ip2regionUtils;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpHeaders;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.springframework.beans.factory.xml.BeanDefinitionParserDelegate.DEFAULT_VALUE;

/**
 * access log aspect
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/10/29
 */
@Aspect
@RequiredArgsConstructor
public class AccessLogAspect implements ApplicationContextAware {

    private final AccessLogProperties accessLogProperties;

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private static final ThreadLocal<AccessLogBean> LOG_BEAN_THREAD_LOCAL = ThreadLocal.withInitial(AccessLogBean::new);

    /**
     * annotation 方法拦截
     */
    @Pointcut("@annotation(com.sunchaser.shushan.mojian.log.annotation.AccessLog)")
    public void annotationPointcut() {
    }

    /**
     * within 类拦截
     */
    @Pointcut("@within(com.sunchaser.shushan.mojian.log.annotation.AccessLog)")
    public void withinPointcut() {
    }

    @Pointcut("annotationPointcut() || withinPointcut()")
    public void accessLogPointcut() {
    }

    @Pointcut("!@annotation(com.sunchaser.shushan.mojian.log.annotation.LogIgnore)")
    public void logIgnorePointcut() {
    }

    @Before("(accessLogPointcut() && logIgnorePointcut()) && (@annotation(accessLog) || @within(accessLog))")
    public void before(JoinPoint joinPoint, AccessLog accessLog) {
        try {
            ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = Objects.requireNonNull(sra).getRequest();
            AccessLogBean alb = LOG_BEAN_THREAD_LOCAL.get();
            String applicationName = applicationContext.getId();
            String activeProfiles = StringUtils.join(applicationContext.getEnvironment().getActiveProfiles(), ",");
            alb.setAppId(Optionals.of(accessLogProperties.getAppId(), applicationName));
            alb.setEnv(Optionals.of(accessLogProperties.getEnv(), Optionals.of(activeProfiles, DEFAULT_VALUE)));
            if (accessLog.enableUserAgent()) {
                String ua = request.getHeader(HttpHeaders.USER_AGENT);
                alb.setUserAgent(ua);
                UserAgent userAgent = UserAgent.parseUserAgentString(ua);
                OperatingSystem os = userAgent.getOperatingSystem();
                alb.setDeviceType(os.getDeviceType().getName());
                alb.setOs(os.getName());
                alb.setBrowser(userAgent.getBrowser().getName());
            }
            String clientIP = ServletUtil.getClientIP(request);
            alb.setRequestIp(clientIP);
            if (accessLog.enableRegion()) {
                alb.setRegion(Ip2regionUtils.searchFriendlyRegion(clientIP));
            }
            alb.setRequestUri(URLUtil.getPath(request.getRequestURI()));
            alb.setRequestMethod(request.getMethod());
            alb.setClassName(joinPoint.getTarget().getClass().getName());
            alb.setMethodName(joinPoint.getSignature().getName());
            alb.setDescription(accessLog.value());
            alb.setStatus(RequestStatus.SUCCESS);
            alb.setAccessType(accessLog.type());
            if (accessLog.enableRequest()) {
                // 方法参数列表
                Object[] args = joinPoint.getArgs();
                // todo 不可序列化 或者 序列化后过大
                alb.setParameters(formatParameters(args));
            }
            alb.setStartTime(LocalDateTime.now());
            LOG_BEAN_THREAD_LOCAL.set(alb);
        } catch (Throwable t) {
            LOG_BEAN_THREAD_LOCAL.remove();
            throw t;
        }
    }

    /**
     * 注意点：一个 AOP 切面存在多个 @Before 方法时的执行顺序
     * 需保证此方法在 before 方法之后执行
     * <p>
     * 测试结果表明：存在多个 @Before 方法时，按方法名首字母从 a-z 顺序执行
     *
     * @param accessLog {@link AccessLog}
     */
    @Before("(annotationPointcut() && logIgnorePointcut()) && @annotation(accessLog)")
    public void beforeAnnotation(AccessLog accessLog) {
        try {
            AccessLogBean alb = LOG_BEAN_THREAD_LOCAL.get();
            // 用方法上的 AccessLog 注解覆盖类上的
            alb.setDescription(accessLog.value());
            alb.setAccessType(accessLog.type());
            LOG_BEAN_THREAD_LOCAL.set(alb);
        } catch (Throwable t) {
            LOG_BEAN_THREAD_LOCAL.remove();
            throw t;
        }
    }

    @AfterReturning(value = "(accessLogPointcut() && logIgnorePointcut()) && (@annotation(accessLog) || @within(accessLog))", returning = "result")
    public void afterReturning(AccessLog accessLog, Object result) {
        try {
            AccessLogBean alb = LOG_BEAN_THREAD_LOCAL.get();
            if (accessLog.enableResponse()) {
                alb.setResponse(JsonUtils.toJsonString(result));
            }
            publishEvent(alb, accessLog.enableRt());
        } catch (Throwable t) {
            LOG_BEAN_THREAD_LOCAL.remove();
            throw t;
        }
    }

    @AfterThrowing(value = "(accessLogPointcut() && logIgnorePointcut()) && (@annotation(accessLog) || @within(accessLog))", throwing = "t")
    public void afterThrowing(AccessLog accessLog, Throwable t) {
        try {
            AccessLogBean alb = LOG_BEAN_THREAD_LOCAL.get();
            alb.setStatus(RequestStatus.EXCEPTION);
            alb.setException(ThrowableUtils.printStackTrace(t));
            publishEvent(alb, accessLog.enableRt());
        } catch (Throwable th) {
            LOG_BEAN_THREAD_LOCAL.remove();
            throw th;
        }
    }

    public void publishEvent(AccessLogBean alb, boolean enableRt) {
        LocalDateTime now = LocalDateTime.now();
        alb.setEndTime(now);
        if (enableRt) {
            alb.setRt(alb.getStartTime().until(now, ChronoUnit.MILLIS));
        }
        applicationContext.publishEvent(new AccessLogEvent(alb));
        LOG_BEAN_THREAD_LOCAL.remove();
    }

    /**
     * 格式化方法请求参数，过滤掉一些不应该被日志记录的对象，例如 {@link MultipartFile} 等。
     *
     * @param args args
     * @return parameters
     */
    private String formatParameters(Object[] args) {
        if (ArrayUtils.isEmpty(args)) {
            return StringUtils.EMPTY;
        }
        List<Object> parameters = Lists.newArrayList();
        for (Object arg : args) {
            if (Objects.isNull(arg)) {
                continue;
            }
            if (shouldNotLog(arg)) {
                continue;
            }
            parameters.add(arg);
        }
        if (CollectionUtils.isEmpty(parameters)) {
            return StringUtils.EMPTY;
        }
        return JsonUtils.toJsonString(parameters);
    }

    /**
     * 判断 arg 是否不应该被日志记录
     *
     * @param arg arg
     * @return true 表示不应该被日志记录
     */
    private boolean shouldNotLog(Object arg) {
        Class<?> clazz = arg.getClass();
        if (clazz.isArray()) {
            return clazz.getComponentType().isAssignableFrom(MultipartFile.class);
        }
        if (Collection.class.isAssignableFrom(clazz)) {
            @SuppressWarnings("unchecked")
            Collection<Object> col = (Collection<Object>) arg;
            for (Object val : col) {
                return val instanceof MultipartFile;
            }
        }
        if (Map.class.isAssignableFrom(clazz)) {
            @SuppressWarnings("unchecked")
            Map<Object, Object> map = (Map<Object, Object>) arg;
            for (Map.Entry<Object, Object> entry : map.entrySet()) {
                return entry.getValue() instanceof MultipartFile;
            }
        }
        return arg instanceof MultipartFile
                || arg instanceof HttpServletRequest
                || arg instanceof HttpServletResponse
                || arg instanceof BindingResult;
    }
}
