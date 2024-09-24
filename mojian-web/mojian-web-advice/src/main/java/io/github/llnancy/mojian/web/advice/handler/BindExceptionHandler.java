package io.github.llnancy.mojian.web.advice.handler;

import com.google.common.collect.Lists;
import io.github.llnancy.mojian.base.entity.response.IResponse;
import io.github.llnancy.mojian.base.enums.ResponseEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * {@link BindException} {@link MethodArgumentNotValidException} handler
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2024/3/15
 */
public class BindExceptionHandler extends AbstractExceptionHandler {

    public BindExceptionHandler() {
        this(Lists.newArrayList(BindException.class, MethodArgumentNotValidException.class), HttpStatus.BAD_REQUEST);
    }

    public BindExceptionHandler(List<Class<? extends Throwable>> exceptionClasses, HttpStatusCode httpStatus) {
        super(exceptionClasses, httpStatus);
    }

    @Override
    protected void doFormatIResponse(Throwable ex) {
        BindException be = (BindException) ex;
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
