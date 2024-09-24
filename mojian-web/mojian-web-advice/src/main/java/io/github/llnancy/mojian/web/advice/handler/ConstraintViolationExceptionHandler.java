package io.github.llnancy.mojian.web.advice.handler;

import com.google.common.collect.Lists;
import io.github.llnancy.mojian.base.entity.response.IResponse;
import io.github.llnancy.mojian.base.enums.ResponseEnum;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.util.List;
import java.util.stream.Collectors;

/**
 * {@link ConstraintViolationException} handler
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2024/3/15
 */
public class ConstraintViolationExceptionHandler extends AbstractExceptionHandler {

    public ConstraintViolationExceptionHandler() {
        this(Lists.newArrayList(ConstraintViolationException.class), HttpStatus.BAD_REQUEST);
    }

    public ConstraintViolationExceptionHandler(List<Class<? extends Throwable>> exceptionClasses, HttpStatusCode httpStatus) {
        super(exceptionClasses, httpStatus);
    }

    @Override
    protected void doFormatIResponse(Throwable ex) {
        ConstraintViolationException cve = (ConstraintViolationException) ex;
        this.setResponse(IResponse.ofFailure(
                ResponseEnum.INVALID_PARAM.getCode(),
                cve.getConstraintViolations()
                        .stream()
                        .map(ConstraintViolation::getMessage)
                        .collect(Collectors.joining(","))
        ));
    }
}
