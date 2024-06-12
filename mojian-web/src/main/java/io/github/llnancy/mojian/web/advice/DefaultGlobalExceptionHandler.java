package io.github.llnancy.mojian.web.advice;

import io.github.llnancy.mojian.base.util.JsonUtils;
import io.github.llnancy.mojian.web.advice.handler.ExceptionHandlerFactory;
import io.github.llnancy.mojian.web.advice.handler.IExceptionHandler;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 默认全局异常处理器
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/07/10
 */
@Slf4j
@RequiredArgsConstructor
public class DefaultGlobalExceptionHandler implements Ordered {

    private final ExceptionHandlerFactory factory;

    @ExceptionHandler(Throwable.class)
    public void handle(Throwable ex, HttpServletResponse response) throws IOException {
        IExceptionHandler match = factory.match(ex);
        log.error("[mojian] - default global exception handler, match handler is {}.", match.getClass(), ex);
        response.setStatus(match.getStatusCode().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(JsonUtils.toJsonString(match.getResponse()));
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
