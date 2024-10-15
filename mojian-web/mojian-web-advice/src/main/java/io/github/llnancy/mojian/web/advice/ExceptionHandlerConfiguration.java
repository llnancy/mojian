package io.github.llnancy.mojian.web.advice;

import io.github.llnancy.mojian.web.advice.handler.AuthenticationExceptionHandler;
import io.github.llnancy.mojian.web.advice.handler.BindExceptionHandler;
import io.github.llnancy.mojian.web.advice.handler.Client4XxExceptionHandler;
import io.github.llnancy.mojian.web.advice.handler.ConstraintViolationExceptionHandler;
import io.github.llnancy.mojian.web.advice.handler.ExceptionHandlerFactory;
import io.github.llnancy.mojian.web.advice.handler.IExceptionHandler;
import io.github.llnancy.mojian.web.advice.handler.Server5XxExceptionHandler;
import jakarta.servlet.ServletException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * {@link IExceptionHandler} configuration
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2024/3/15
 */
public class ExceptionHandlerConfiguration {

    @Bean
    public BindExceptionHandler bindExceptionHandler() {
        return new BindExceptionHandler();
    }

    @Bean
    @ConditionalOnClass(name = {"org.springframework.security.core.AuthenticationException"})
    public AuthenticationExceptionHandler authenticationExceptionHandler() {
        return new AuthenticationExceptionHandler();
    }

    @Bean
    @ConditionalOnClass({ServletException.class})
    public Client4XxExceptionHandler client4xxExceptionHandler() {
        return new Client4XxExceptionHandler();
    }

    @Bean
    public ConstraintViolationExceptionHandler constraintViolationExceptionHandler() {
        return new ConstraintViolationExceptionHandler();
    }

    @Bean
    public Server5XxExceptionHandler server5xxExceptionHandler() {
        return new Server5XxExceptionHandler();
    }

    @Bean
    public ExceptionHandlerFactory exceptionHandlerFactory(ApplicationContext applicationContext) {
        ExceptionHandlerFactory factory = new ExceptionHandlerFactory();
        factory.setApplicationContext(applicationContext);
        // order?
        factory.init();
        return factory;
    }
}
