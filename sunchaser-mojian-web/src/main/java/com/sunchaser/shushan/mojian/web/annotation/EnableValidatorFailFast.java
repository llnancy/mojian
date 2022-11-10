package com.sunchaser.shushan.mojian.web.annotation;

import com.sunchaser.shushan.mojian.web.config.ValidatorConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * enable validator fail-fast
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/10/29
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(ValidatorConfiguration.class)
public @interface EnableValidatorFailFast {
}
