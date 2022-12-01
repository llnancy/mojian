package com.sunchaser.shushan.mojian.log.aspect;

import cn.hutool.core.util.URLUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.sunchaser.shushan.mojian.base.util.JsonUtils;
import com.sunchaser.shushan.mojian.base.util.Optionals;
import com.sunchaser.shushan.mojian.base.util.ThrowableUtils;
import com.sunchaser.shushan.mojian.log.annotation.AccessLog;
import com.sunchaser.shushan.mojian.log.config.property.AccessLogProperties;
import com.sunchaser.shushan.mojian.log.entity.AccessLogBean;
import com.sunchaser.shushan.mojian.log.event.AccessLogEvent;
import com.sunchaser.shushan.mojian.log.util.Ip2regionUtils;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.RequiredArgsConstructor;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static com.sunchaser.shushan.mojian.log.entity.AccessLogBean.RequestStatus;
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
     * within 类拦截
     */
    @Pointcut("@annotation(com.sunchaser.shushan.mojian.log.annotation.AccessLog) || @within(com.sunchaser.shushan.mojian.log.annotation.AccessLog)")
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
                String uaHeader = request.getHeader(HttpHeaders.USER_AGENT);
                UserAgent userAgent = UserAgent.parseUserAgentString(uaHeader);
                OperatingSystem os = userAgent.getOperatingSystem();
                alb.setDeviceType(os.getDeviceType().toString());
                alb.setOs(os.getName());
                alb.setBrowser(userAgent.getBrowser().toString());
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
            alb.setRequestStatus(RequestStatus.SUCCESS);
            if (accessLog.enableRequest()) {
                // 方法参数列表
                Object[] args = joinPoint.getArgs();
                // todo 不可序列化 或者 序列化后过大
                alb.setParameters(JsonUtils.toJsonString(args));
            }
            alb.setStartTime(LocalDateTime.now());
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
            alb.setRequestStatus(RequestStatus.EXCEPTION);
            alb.setErrorMsg(ThrowableUtils.printStackTrace(t));
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
}
