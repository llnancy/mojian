package com.sunchaser.shushan.mojian.log.aspect;

import cn.hutool.core.util.URLUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.Header;
import com.sunchaser.shushan.mojian.base.util.JsonUtils;
import com.sunchaser.shushan.mojian.base.util.Optionals;
import com.sunchaser.shushan.mojian.base.util.ThrowableUtils;
import com.sunchaser.shushan.mojian.log.annotation.MjLog;
import com.sunchaser.shushan.mojian.log.config.MjLogProperties;
import com.sunchaser.shushan.mojian.log.entity.MjLogBean;
import com.sunchaser.shushan.mojian.log.event.MjLogEvent;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static com.sunchaser.shushan.mojian.log.entity.MjLogBean.RequestStatus;

/**
 * mj log aspect
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/10/29
 */
@Aspect
@RequiredArgsConstructor
public class MjLogAspect implements ApplicationContextAware {

    private final MjLogProperties logProperties;

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private static final ThreadLocal<MjLogBean> LOG_BEAN_THREAD_LOCAL = ThreadLocal.withInitial(MjLogBean::new);

    @Pointcut("@annotation(com.sunchaser.shushan.mojian.log.annotation.MjLog)")
    public void mjLogPointCut() {
    }

    @Before("mjLogPointCut() && @annotation(mjLog)")
    public void before(JoinPoint joinPoint, MjLog mjLog) {
        try {
            ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = Objects.requireNonNull(sra).getRequest();
            MjLogBean logBean = LOG_BEAN_THREAD_LOCAL.get();
            Environment environment = applicationContext.getEnvironment();
            String applicationName = environment.getProperty("spring.application.name", "default");
            String active = environment.getProperty("spring.profiles.active", "default");
            logBean.setAppId(Optionals.of(logProperties.getAppId(), applicationName));
            logBean.setEnv(Optionals.of(logProperties.getEnv(), active));
            logBean.setUserAgent(request.getHeader(Header.USER_AGENT.getValue()));
            logBean.setRequestIp(ServletUtil.getClientIP(request));
            logBean.setRequestUri(URLUtil.getPath(request.getRequestURI()));
            logBean.setRequestMethod(request.getMethod());
            // 类名
            String className = joinPoint.getTarget().getClass().getName();
            // 方法名
            String methodName = joinPoint.getSignature().getName();
            logBean.setService(className + "#" + methodName);
            logBean.setRequestStatus(RequestStatus.SUCCESS);
            if (mjLog.enableParameters()) {
                // 方法参数列表
                Object[] args = joinPoint.getArgs();
                logBean.setParameters(JsonUtils.toJsonString(args));
            }
            logBean.setStartTime(LocalDateTime.now());
            LOG_BEAN_THREAD_LOCAL.set(logBean);
        } catch (Throwable t) {
            LOG_BEAN_THREAD_LOCAL.remove();
        }
    }

    @AfterReturning(pointcut = "mjLogPointCut() && @annotation(mjLog)", returning = "result")
    public void afterReturning(JoinPoint joinPoint, MjLog mjLog, Object result) {
        try {
            MjLogBean logBean = LOG_BEAN_THREAD_LOCAL.get();
            if (mjLog.enableResponse()) {
                logBean.setResponse(JsonUtils.toJsonString(result));
            }
            publishEvent(logBean);
        } catch (Throwable t) {
            LOG_BEAN_THREAD_LOCAL.remove();
        }
    }

    @AfterThrowing(pointcut = "mjLogPointCut()", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, Throwable e) {
        try {
            MjLogBean logBean = LOG_BEAN_THREAD_LOCAL.get();
            logBean.setRequestStatus(RequestStatus.EXCEPTION);
            logBean.setErrorMsg(ThrowableUtils.printStackTrace(e));
            publishEvent(logBean);
        } catch (Throwable t) {
            LOG_BEAN_THREAD_LOCAL.remove();
        }
    }

    public void publishEvent(MjLogBean logBean) {
        LocalDateTime now = LocalDateTime.now();
        logBean.setEndTime(now);
        logBean.setRt(logBean.getStartTime().until(now, ChronoUnit.MILLIS));
        applicationContext.publishEvent(new MjLogEvent(logBean));
        LOG_BEAN_THREAD_LOCAL.remove();
    }
}
