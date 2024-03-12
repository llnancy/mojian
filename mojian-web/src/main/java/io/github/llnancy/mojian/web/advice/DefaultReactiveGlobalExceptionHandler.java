package io.github.llnancy.mojian.web.advice;

import io.github.llnancy.mojian.base.entity.response.IResponse;
import io.github.llnancy.mojian.base.enums.ResponseEnum;
import io.github.llnancy.mojian.base.exception.MjBaseBizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.MissingRequestValueException;
import reactor.core.publisher.Mono;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * reactive global exception handler
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/8/15
 */
@Slf4j
public class DefaultReactiveGlobalExceptionHandler {

    @ExceptionHandler({
            SQLIntegrityConstraintViolationException.class,
            RuntimeException.class,
            MjBaseBizException.class
    })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<IResponse> handle5xxServerError(Exception ex) {
        log.error("DefaultReactiveGlobalExceptionHandler#handle5xxServerError", ex);
        return Mono.just(IResponse.ofFailure(ResponseEnum.FAILURE.getCode(), ResponseEnum.FAILURE.getMsg()));
    }

    @ExceptionHandler({
            WebExchangeBindException.class,
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<IResponse> handleWebExchangeBindException(WebExchangeBindException ex) {
        log.error("DefaultReactiveGlobalExceptionHandler#handleWebExchangeBindException", ex);
        return Mono.just(
                IResponse.ofFailure(
                        ResponseEnum.INVALID_PARAM.getCode(),
                        ex.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .filter(Objects::nonNull)
                                .map(FieldError::getDefaultMessage)
                                .collect(Collectors.joining(","))
                )
        );
    }

    @ExceptionHandler({MissingRequestValueException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<IResponse> handleMissingRequestValueException(MissingRequestValueException ex) {
        log.error("DefaultReactiveGlobalExceptionHandler#handleMissingRequestValueException", ex);
        return Mono.just(IResponse.ofFailure(ResponseEnum.INVALID_PARAM.getCode(), ex.getReason()));
    }
}
