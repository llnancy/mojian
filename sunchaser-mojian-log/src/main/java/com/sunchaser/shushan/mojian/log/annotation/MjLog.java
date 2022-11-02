package com.sunchaser.shushan.mojian.log.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * mj log
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/10/29
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MjLog {

    String value() default "";

    boolean enableParameters() default true;

    boolean enableResponse() default true;
}
