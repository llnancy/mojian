package io.github.llnancy.mojian.log.annotation;

import io.github.llnancy.mojian.log.enums.AccessType;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * access log
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/07/10
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccessLog {

    /**
     * 日志文本内容
     *
     * @return 日志文本
     * @since 0.0.2 修饰在方法上时支持 SpEL 表达式
     */
    String value() default "";

    AccessType type() default AccessType.OTHER;

    boolean enableUserAgent() default true;

    boolean enableRegion() default true;

    boolean enableRequest() default true;

    boolean enableResponse() default true;

    boolean enableRt() default true;
}
