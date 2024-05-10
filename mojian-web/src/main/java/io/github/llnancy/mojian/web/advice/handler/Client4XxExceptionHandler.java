package io.github.llnancy.mojian.web.advice.handler;

import com.google.common.collect.Lists;
import io.github.llnancy.mojian.base.entity.response.IResponse;
import io.github.llnancy.mojian.base.enums.ResponseEnum;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;

/**
 * {@link HttpRequestMethodNotSupportedException}
 * {@link HttpMediaTypeNotSupportedException}
 * {@link HttpMediaTypeNotAcceptableException}
 * {@link MissingPathVariableException}
 * {@link MissingServletRequestParameterException}
 * {@link ServletRequestBindingException}
 * {@link ConversionNotSupportedException}
 * {@link TypeMismatchException}
 * {@link HttpMessageNotReadableException}
 * {@link HttpMessageNotWritableException}
 * {@link MissingServletRequestPartException}
 * {@link NoHandlerFoundException}
 * {@link AsyncRequestTimeoutException}
 * {@link IllegalArgumentException}
 * handler
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2024/3/15
 */
public class Client4XxExceptionHandler extends AbstractExceptionHandler {

    public Client4XxExceptionHandler() {
        this(
                Lists.newArrayList(
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
                        IllegalArgumentException.class
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    public Client4XxExceptionHandler(List<Class<? extends Throwable>> exceptionClasses, HttpStatusCode httpStatus) {
        super(exceptionClasses, httpStatus);
    }

    @Override
    protected void doFormatIResponse(Throwable ex) {
        this.setResponse(IResponse.ofFailure(ResponseEnum.INVALID_PARAM.getCode(), ex.getMessage()));
    }
}
