package io.github.llnancy.mojian.log.aspect;

import cn.hutool.core.util.URLUtil;
import cn.hutool.extra.servlet.JakartaServletUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import io.github.llnancy.mojian.base.util.JsonUtils;
import io.github.llnancy.mojian.base.util.Optionals;
import io.github.llnancy.mojian.desensitize.support.DesensitizeConfiguration;
import io.github.llnancy.mojian.log.annotation.AccessLog;
import io.github.llnancy.mojian.log.config.property.AccessLogProperties;
import io.github.llnancy.mojian.log.entity.AccessLogBean;
import io.github.llnancy.mojian.log.enums.AccessType;
import io.github.llnancy.mojian.log.enums.RequestStatus;
import io.github.llnancy.mojian.log.event.AccessLogEvent;
import io.github.llnancy.mojian.log.util.Ip2regionUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
import org.springframework.context.expression.StandardBeanExpressionResolver;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

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
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/07/10
 */
@Aspect
@RequiredArgsConstructor
public class AccessLogAspect implements ApplicationContextAware {

    private static final ObjectMapper OBJECT_MAPPER = JsonUtils.createObjectMapper();

    static {
        JsonUtils.commonInit(OBJECT_MAPPER);
        DesensitizeConfiguration.configureAnnotationIntrospector(OBJECT_MAPPER);
    }

    private final AccessLogProperties accessLogProperties;

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private static final ThreadLocal<AccessLogAspectContext> ASPECT_CONTEXT_THREAD_LOCAL = ThreadLocal.withInitial(AccessLogAspectContext::new);

    private static final ExpressionParser EXPRESSION_PARSER = new SpelExpressionParser();

    private static final DefaultParameterNameDiscoverer NAME_DISCOVERER = new DefaultParameterNameDiscoverer();

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
            configureAppId(alb);
            configureEnv(alb);
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            // 目标方法
            Method method = signature.getMethod();
            Class<?> clazz = joinPoint.getTarget().getClass();
            // 方法上的注解
            AccessLog methodAccessLog = method.getDeclaredAnnotation(AccessLog.class);
            // 类上的注解
            AccessLog classAccessLog = findClassAccessLog(clazz);
            context.setMethodAccessLog(methodAccessLog);
            context.setClassAccessLog(classAccessLog);
            ASPECT_CONTEXT_THREAD_LOCAL.set(context);
            // 方法参数名称列表
            String[] parameterNames = NAME_DISCOVERER.getParameterNames(method);
            // 方法参数值列表
            Object[] args = joinPoint.getArgs();
            // 方法上的注解优先级最高，其值如果存在，则覆盖类上的注解，否则使用注解的默认值。但是 value 字段除外。
            // eg. 类上的注解指定 value = "类操作"，方法上的注解如果指定了 value = "方法操作"，则 value = "方法操作"；否则 value = "类操作"。
            // 除 value 字段外，其它字段无法判定是显式声明了值还是使用了默认值。
            if (Objects.isNull(methodAccessLog)) {
                alb.setDescription(classAccessLog.value());
            } else {
                String value = methodAccessLog.value();
                alb.setDescription(parseExpression(value, parameterNames, args));
            }
            boolean enableUserAgent = context.enableUserAgent();
            if (enableUserAgent) {
                configureUserAgent(request, alb);
            }
            String clientIP = JakartaServletUtil.getClientIP(request);
            alb.setRequestIp(clientIP);
            boolean enableRegion = context.enableRegion();
            if (enableRegion) {
                alb.setRegion(Ip2regionUtils.searchFriendlyRegion(clientIP));
            }
            alb.setRequestUri(URLUtil.getPath(request.getRequestURI()));
            alb.setRequestMethod(request.getMethod());
            alb.setClassName(clazz.getName());
            alb.setMethodName(method.getName());
            alb.setStatus(RequestStatus.SUCCESS);
            AccessType accessType = context.accessType();
            alb.setAccessType(accessType);
            boolean enableRequest = context.enableRequest();
            if (enableRequest) {
                // todo 不可序列化 或者 序列化后过大
                alb.setParameters(formatParameters(args));
            }
            alb.setStartTime(LocalDateTime.now());
        } catch (Throwable t) {
            ASPECT_CONTEXT_THREAD_LOCAL.remove();
            throw t;
        }
    }

    private void configureEnv(AccessLogBean alb) {
        String activeProfiles = StringUtils.join(applicationContext.getEnvironment().getActiveProfiles(), ",");
        alb.setEnv(Optionals.of(accessLogProperties.getEnv(), Optionals.of(activeProfiles, DEFAULT_VALUE)));
    }

    private void configureAppId(AccessLogBean alb) {
        String applicationName = applicationContext.getId();
        alb.setAppId(Optionals.of(accessLogProperties.getAppId(), applicationName));
    }

    private String parseExpression(String value, String[] parameterNames, Object[] args) {
        if (StringUtils.isBlank(value) || !StringUtils.contains(value, StandardBeanExpressionResolver.DEFAULT_EXPRESSION_PREFIX)) {
            return value;
        }
        StandardEvaluationContext context = new StandardEvaluationContext();
        for (int i = 0; i < args.length; i++) {
            context.setVariable(parameterNames[i], args[i]);
        }
        return EXPRESSION_PARSER.parseExpression(value).getValue(context, String.class);
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
            boolean enableResponse = context.enableResponse();
            AccessType accessType = context.accessType();
            if (enableResponse && accessType != AccessType.LOGIN) {
                alb.setResponse(JsonUtils.doToJsonString(result, OBJECT_MAPPER));
            }
            boolean enableRt = context.enableRt();
            publishEvent(alb, enableRt);
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
            boolean enableRt = context.enableRt();
            alb.setStatus(RequestStatus.EXCEPTION);
            alb.setException(Throwables.getStackTraceAsString(t));
            publishEvent(alb, enableRt);
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
        if (parameters.size() == 1) {
            Object obj = parameters.get(0);
            return JsonUtils.doToJsonString(obj, OBJECT_MAPPER);
        }
        return JsonUtils.doToJsonString(parameters, OBJECT_MAPPER);
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
