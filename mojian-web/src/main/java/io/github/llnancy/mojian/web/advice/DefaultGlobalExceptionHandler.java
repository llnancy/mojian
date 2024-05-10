package io.github.llnancy.mojian.web.advice;

import io.github.llnancy.mojian.base.util.JsonUtils;
import io.github.llnancy.mojian.web.advice.handler.ExceptionHandlerFactory;
import io.github.llnancy.mojian.web.advice.handler.IExceptionHandler;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

/**
 * 默认全局异常处理器
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/07/10
 */
@Slf4j
public class DefaultGlobalExceptionHandler implements Ordered {

    @ExceptionHandler(Throwable.class)
    public void handle(Throwable ex, ExceptionHandlerFactory exceptionHandlerFactory, HttpServletResponse response) throws IOException {
        IExceptionHandler match = exceptionHandlerFactory.match(ex);
        log.error("[mojian] - default global exception handler, match handler is {}.", match.getClass(), ex);
        response.setStatus(match.getStatusCode().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(JsonUtils.toJsonString(match.getResponse()));
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
