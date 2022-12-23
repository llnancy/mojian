package io.github.llnancy.mojian.desensitize.annotation;

import io.github.llnancy.mojian.desensitize.strategy.DesensitizeStrategy;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * data desensitize annotation
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/12/16
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Desensitize {

    Class<? extends DesensitizeStrategy> strategy() default DesensitizeStrategy.class;

    String placeholder() default DesensitizeStrategy.DEFAULT_PLACEHOLDER;
}
