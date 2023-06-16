package io.github.llnancy.mojian.desensitize.annotation;

import io.github.llnancy.mojian.desensitize.support.DesensitizeConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Enable @Desensitize
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/12/28
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(DesensitizeConfiguration.class)
public @interface EnableMjDesensitize {
}
