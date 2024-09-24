package io.github.llnancy.mojian.web.reactive.advice.annotation;

import io.github.llnancy.mojian.web.reactive.advice.ReactiveExceptionHandlerConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * enable reactive global exception handler
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2024/3/15
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(ReactiveExceptionHandlerConfiguration.class)
public @interface EnableReactiveGlobalExceptionHandler {
}
