package com.sunchaser.shushan.mojian.log.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * access log
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/10/29
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccessLog {

    String value() default "";

    boolean enableRequest() default true;

    boolean enableResponse() default true;

    boolean enableRt() default true;
}
