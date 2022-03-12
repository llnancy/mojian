package com.sunchaser.mojian.web.advice;

import com.sunchaser.mojian.base.entity.response.IResponse;
import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.sunchaser.mojian.base.Constants.StringConstants.EN_COMMA;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/10/25
 */
@RestControllerAdvice
public class MjExceptionHandler {

    @Bean
    public Validator validator() {
        return Validation.byProvider(HibernateValidator.class)
                .configure()
                .failFast(true)
                .buildValidatorFactory()
                .getValidator();
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public IResponse handleBindException(BindException be) {
        return IResponse.ofFailure(
                IResponse.INVALID_PARAM.getResultCode(),
                be.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .filter(Objects::nonNull)
                        .map(FieldError::getDefaultMessage)
                        .collect(Collectors.joining(EN_COMMA))
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public IResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException mve) {
        return IResponse.ofFailure(
                IResponse.INVALID_PARAM.getResultCode(),
                mve.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .filter(Objects::nonNull)
                        .map(FieldError::getDefaultMessage)
                        .collect(Collectors.joining(EN_COMMA))
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public IResponse handleMethodArgumentNotValidException(ConstraintViolationException cve) {
        return IResponse.ofFailure(
                IResponse.INVALID_PARAM.getResultCode(),
                cve.getConstraintViolations()
                        .stream()
                        .map(ConstraintViolation::getMessage)
                        .collect(Collectors.joining(EN_COMMA))
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public IResponse handleIllegalArgumentException(IllegalArgumentException iae) {
        return IResponse.ofFailure(IResponse.INVALID_PARAM.getResultCode(), iae.getMessage());
    }
}
