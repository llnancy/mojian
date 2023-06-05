package io.github.llnancy.mojian.web.annotation;

import io.github.llnancy.mojian.web.config.JwtProviderAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * enable jwt
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/6/3
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(JwtProviderAutoConfiguration.class)
public @interface EnableMjJwt {
}
