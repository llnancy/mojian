package io.github.llnancy.mojian.web.reactive.advice;

import io.github.llnancy.mojian.web.advice.handler.ExceptionHandlerFactory;
import io.github.llnancy.mojian.web.advice.handler.IExceptionHandler;
import io.github.llnancy.mojian.web.reactive.advice.handler.ReactiveClient4xxExceptionHandler;
import io.github.llnancy.mojian.web.reactive.advice.handler.WebExchangeBindExceptionHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.reactive.function.server.RouterFunctions;

/**
 * reactive {@link IExceptionHandler} configuration
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2024/9/23
 */
public class ReactiveExceptionHandlerConfiguration {

    @Bean
    public ReactiveClient4xxExceptionHandler reactiveClient4xxExceptionHandler() {
        return new ReactiveClient4xxExceptionHandler();
    }

    @Bean
    public WebExchangeBindExceptionHandler webExchangeBindExceptionHandler() {
        return new WebExchangeBindExceptionHandler();
    }

    @Bean
    @DependsOn("exceptionHandlerFactory")
    @ConditionalOnClass({RouterFunctions.class})
    public DefaultReactiveGlobalExceptionHandler defaultReactiveGlobalExceptionHandler(ExceptionHandlerFactory factory) {
        return new DefaultReactiveGlobalExceptionHandler(factory);
    }
}
