package io.github.llnancy.mojian.web.reactive.advice;

import io.github.llnancy.mojian.base.util.JsonUtils;
import io.github.llnancy.mojian.web.advice.handler.ExceptionHandlerFactory;
import io.github.llnancy.mojian.web.advice.handler.IExceptionHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * reactive global exception handler
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/8/15
 */
@Slf4j
@RequiredArgsConstructor
public class DefaultReactiveGlobalExceptionHandler implements ErrorWebExceptionHandler, Ordered {

    private final ExceptionHandlerFactory factory;

    @NonNull
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, @NonNull Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();
        if (response.isCommitted()) {
            return Mono.error(ex);
        }
        IExceptionHandler match = factory.match(ex);
        log.error("[mojian] - default reactive global exception handler, match handler is {}.", match.getClass(), ex);
        response.setStatusCode(match.getStatusCode());
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        return response.writeWith(Mono.just(response.bufferFactory().wrap(JsonUtils.toBytes(match.getResponse()))));
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
