package io.github.llnancy.mojian.web.reactive.advice.handler;

import com.google.common.collect.Lists;
import io.github.llnancy.mojian.base.entity.response.IResponse;
import io.github.llnancy.mojian.base.enums.ResponseEnum;
import io.github.llnancy.mojian.web.advice.handler.AbstractExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * {@link WebExchangeBindException} handler
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2024/3/15
 */
public class WebExchangeBindExceptionHandler extends AbstractExceptionHandler {

    public WebExchangeBindExceptionHandler() {
        this(Lists.newArrayList(WebExchangeBindException.class), HttpStatus.BAD_REQUEST);
    }

    public WebExchangeBindExceptionHandler(List<Class<? extends Throwable>> exceptionClasses, HttpStatusCode httpStatus) {
        super(exceptionClasses, httpStatus);
    }

    @Override
    protected void doFormatIResponse(Throwable ex) {
        WebExchangeBindException be = (WebExchangeBindException) ex;
        this.setResponse(IResponse.ofFailure(
                ResponseEnum.INVALID_PARAM.getCode(),
                be.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .filter(Objects::nonNull)
                        .map(FieldError::getDefaultMessage)
                        .collect(Collectors.joining(","))
        ));
    }
}
