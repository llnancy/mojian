package io.github.llnancy.mojian.log.aspect;

import cn.hutool.core.util.URLUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.google.common.collect.Lists;
import io.github.llnancy.mojian.base.util.JsonUtils;
import io.github.llnancy.mojian.base.util.Optionals;
import io.github.llnancy.mojian.base.util.ThrowableUtils;
import io.github.llnancy.mojian.log.annotation.AccessLog;
import io.github.llnancy.mojian.log.config.property.AccessLogProperties;
import io.github.llnancy.mojian.log.entity.AccessLogBean;
import io.github.llnancy.mojian.log.enums.AccessType;
import io.github.llnancy.mojian.log.enums.RequestStatus;
import io.github.llnancy.mojian.log.event.AccessLogEvent;
import io.github.llnancy.mojian.log.util.Ip2regionUtils;
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
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpHeaders;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
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
    public void setApplicationContext(@Nonnull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private static final ThreadLocal<AccessLogAspectContext> ASPECT_CONTEXT_THREAD_LOCAL = ThreadLocal.withInitial(AccessLogAspectContext::new);

    /**
     * within 类拦截
     */
    @Pointcut("@within(io.github.llnancy.mojian.log.annotation.AccessLog)")
    public void withinPointcut() {
    }

    /**
     * annotation 方法拦截
     */
    @Pointcut("@annotation(io.github.llnancy.mojian.log.annotation.AccessLog)")
    public void annotationPointcut() {
    }

    @Pointcut("withinPointcut() || annotationPointcut()")
    public void classOrMethodAccessLogPointcut() {
    }

    @Pointcut("!@annotation(io.github.llnancy.mojian.log.annotation.LogIgnore)")
    public void logIgnorePointcut() {
    }

    @Pointcut("classOrMethodAccessLogPointcut() && logIgnorePointcut()")
    public void accessLogPointcut() {
    }

    @Before(value = "accessLogPointcut()")
    public void before(JoinPoint joinPoint) {
        try {
            ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = Objects.requireNonNull(sra).getRequest();
            AccessLogAspectContext context = ASPECT_CONTEXT_THREAD_LOCAL.get();
            AccessLogBean alb = context.getAccessLogBean();
            String applicationName = applicationContext.getId();
            String activeProfiles = StringUtils.join(applicationContext.getEnvironment().getActiveProfiles(), ",");
            alb.setAppId(Optionals.of(accessLogProperties.getAppId(), applicationName));
            alb.setEnv(Optionals.of(accessLogProperties.getEnv(), Optionals.of(activeProfiles, DEFAULT_VALUE)));
            Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
            Class<?> clazz = joinPoint.getTarget().getClass();
            // 方法上的注解
            AccessLog accessLog = method.getDeclaredAnnotation(AccessLog.class);
            // 类上的注解
            AccessLog classAccessLog = findClassAccessLog(clazz);
            classAccessLog = Optionals.of(classAccessLog, accessLog);
            accessLog = Optionals.of(accessLog, classAccessLog);
            if (accessLog.enableUserAgent()) {
                configureUserAgent(request, alb);
            }
            String clientIP = ServletUtil.getClientIP(request);
            alb.setRequestIp(clientIP);
            if (accessLog.enableRegion()) {
                alb.setRegion(Ip2regionUtils.searchFriendlyRegion(clientIP));
            }
            alb.setRequestUri(URLUtil.getPath(request.getRequestURI()));
            alb.setRequestMethod(request.getMethod());
            alb.setClassName(clazz.getName());
            alb.setMethodName(method.getName());
            // 方法上的注解优先级最高，其值如果存在，则覆盖类上的注解，否则使用注解的默认值。但是 value 字段除外。
            // eg. 类上的注解指定 value = "类操作"，方法上的注解如果指定了 value = "方法操作"，则 value = "方法操作"；否则 value = "类操作"。
            // 除 value 字段外，其它字段无法判定是显式声明了值还是使用了默认值。
            alb.setDescription(StringUtils.getIfBlank(accessLog.value(), classAccessLog::value));
            alb.setStatus(RequestStatus.SUCCESS);
            AccessType accessType = accessLog.type();
            alb.setAccessType(accessType);
            if (accessLog.enableRequest() && accessType != AccessType.LOGIN) {
                // 方法参数列表
                Object[] args = joinPoint.getArgs();
                // todo 不可序列化 或者 序列化后过大
                alb.setParameters(formatParameters(args));
            }
            alb.setStartTime(LocalDateTime.now());
            context.setAccessLog(accessLog);
            ASPECT_CONTEXT_THREAD_LOCAL.set(context);
        } catch (Throwable t) {
            ASPECT_CONTEXT_THREAD_LOCAL.remove();
            throw t;
        }
    }

    private static AccessLog findClassAccessLog(Class<?> clazz) {
        AccessLog classAccessLog = clazz.getDeclaredAnnotation(AccessLog.class);
        if (Objects.isNull(classAccessLog)) {
            // 接口上的注解
            for (Class<?> ci : clazz.getInterfaces()) {
                classAccessLog = ci.getDeclaredAnnotation(AccessLog.class);
                if (Objects.nonNull(classAccessLog)) {
                    break;
                }
            }
        }
        return classAccessLog;
    }

    private static void configureUserAgent(HttpServletRequest request, AccessLogBean alb) {
        String ua = request.getHeader(HttpHeaders.USER_AGENT);
        alb.setUserAgent(ua);
        UserAgent userAgent = UserAgent.parseUserAgentString(ua);
        OperatingSystem os = userAgent.getOperatingSystem();
        alb.setDeviceType(os.getDeviceType().getName());
        alb.setOs(os.getName());
        alb.setBrowser(userAgent.getBrowser().getName());
    }

    @AfterReturning(value = "accessLogPointcut()", returning = "result")
    public void afterReturning(Object result) {
        try {
            AccessLogAspectContext context = ASPECT_CONTEXT_THREAD_LOCAL.get();
            AccessLogBean alb = context.getAccessLogBean();
            AccessLog accessLog = context.getAccessLog();
            if (accessLog.enableResponse() && accessLog.type() != AccessType.LOGIN) {
                alb.setResponse(JsonUtils.toJsonString(result));
            }
            publishEvent(alb, accessLog.enableRt());
        } catch (Throwable t) {
            ASPECT_CONTEXT_THREAD_LOCAL.remove();
            throw t;
        }
    }

    @AfterThrowing(value = "accessLogPointcut()", throwing = "t")
    public void afterThrowing(Throwable t) {
        try {
            AccessLogAspectContext context = ASPECT_CONTEXT_THREAD_LOCAL.get();
            AccessLogBean alb = context.getAccessLogBean();
            AccessLog accessLog = context.getAccessLog();
            alb.setStatus(RequestStatus.EXCEPTION);
            alb.setException(ThrowableUtils.printStackTrace(t));
            publishEvent(alb, accessLog.enableRt());
        } catch (Throwable th) {
            ASPECT_CONTEXT_THREAD_LOCAL.remove();
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
        ASPECT_CONTEXT_THREAD_LOCAL.remove();
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
