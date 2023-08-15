package io.github.llnancy.mojian.web.advice;

import io.github.llnancy.mojian.base.entity.response.IResponse;
import io.github.llnancy.mojian.base.enums.ResponseEnum;
import io.github.llnancy.mojian.base.exception.MjBaseBizException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 默认全局异常处理器
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/07/10
 */
@Slf4j
public class DefaultGlobalExceptionHandler {

    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public IResponse handleBindException(BindException be) {
        log.error("DefaultGlobalExceptionHandler#handleBindException", be);
        return IResponse.ofFailure(
                ResponseEnum.INVALID_PARAM.getCode(),
                be.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .filter(Objects::nonNull)
                        .map(FieldError::getDefaultMessage)
                        .collect(Collectors.joining(","))
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public IResponse handleConstraintViolationException(ConstraintViolationException cve) {
        log.error("DefaultGlobalExceptionHandler#handleConstraintViolationException", cve);
        return IResponse.ofFailure(
                ResponseEnum.INVALID_PARAM.getCode(),
                cve.getConstraintViolations()
                        .stream()
                        .map(ConstraintViolation::getMessage)
                        .collect(Collectors.joining(","))
        );
    }

    @ExceptionHandler({
            HttpRequestMethodNotSupportedException.class,
            HttpMediaTypeNotSupportedException.class,
            HttpMediaTypeNotAcceptableException.class,
            MissingPathVariableException.class,
            MissingServletRequestParameterException.class,
            ServletRequestBindingException.class,
            ConversionNotSupportedException.class,
            TypeMismatchException.class,
            HttpMessageNotReadableException.class,
            HttpMessageNotWritableException.class,
            MissingServletRequestPartException.class,
            NoHandlerFoundException.class,
            AsyncRequestTimeoutException.class,
            IllegalArgumentException.class,
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public IResponse handle4xxClientError(Exception ex) {
        log.error("DefaultGlobalExceptionHandler#handle4xxClientError", ex);
        return IResponse.ofFailure(ResponseEnum.INVALID_PARAM.getCode(), ex.getMessage());
    }

    @ExceptionHandler({
            SQLIntegrityConstraintViolationException.class,
            RuntimeException.class,
            MjBaseBizException.class
    })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public IResponse handle5xxServerError(Exception ex) {
        log.error("DefaultGlobalExceptionHandler#handle5xxServerError", ex);
        return IResponse.ofFailure(ResponseEnum.FAILURE.getCode(), ex.getMessage());
    }
}
