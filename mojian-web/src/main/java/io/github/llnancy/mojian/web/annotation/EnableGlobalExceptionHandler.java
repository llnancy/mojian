package io.github.llnancy.mojian.web.annotation;

import io.github.llnancy.mojian.web.advice.ExceptionHandlerConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * enable global exception handler
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2024/3/15
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(ExceptionHandlerConfiguration.class)
public @interface EnableGlobalExceptionHandler {
}
