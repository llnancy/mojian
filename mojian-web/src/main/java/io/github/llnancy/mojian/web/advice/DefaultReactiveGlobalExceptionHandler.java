package io.github.llnancy.mojian.web.advice;

import io.github.llnancy.mojian.base.entity.response.IResponse;
import io.github.llnancy.mojian.base.enums.ResponseEnum;
import io.github.llnancy.mojian.base.exception.MjBaseBizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import reactor.core.publisher.Mono;

import java.sql.SQLIntegrityConstraintViolationException;

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
}
