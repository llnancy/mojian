package io.github.llnancy.mojian.web.time.formatter.annotation;

import io.github.llnancy.mojian.web.time.formatter.config.JacksonConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * enable jackson local-date-time formatter config
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/07/10
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(JacksonConfiguration.class)
public @interface EnableJacksonLocalDateTimeFormatter {
}
