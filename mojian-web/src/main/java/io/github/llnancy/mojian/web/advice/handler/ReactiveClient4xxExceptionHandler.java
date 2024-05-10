package io.github.llnancy.mojian.web.advice.handler;

import com.google.common.collect.Lists;
import io.github.llnancy.mojian.base.entity.response.IResponse;
import io.github.llnancy.mojian.base.enums.ResponseEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.server.MissingRequestValueException;
import org.springframework.web.server.NotAcceptableStatusException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebInputException;
import org.springframework.web.server.UnsatisfiedRequestParameterException;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;

import java.util.List;

/**
 * {@link MethodNotAllowedException}
 * {@link ResponseStatusException}
 * {@link MissingRequestValueException}
 * {@link ServerWebInputException}
 * {@link NotAcceptableStatusException}
 * {@link UnsatisfiedRequestParameterException}
 * {@link UnsupportedMediaTypeStatusException}
 * reactive client 4xx exception handler
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2024/3/16
 */
public class ReactiveClient4xxExceptionHandler extends AbstractExceptionHandler {

    public ReactiveClient4xxExceptionHandler() {
        this(
                Lists.newArrayList(
                        MethodNotAllowedException.class,
                        ResponseStatusException.class,
                        MissingRequestValueException.class,
                        ServerWebInputException.class,
                        NotAcceptableStatusException.class,
                        UnsatisfiedRequestParameterException.class,
                        UnsupportedMediaTypeStatusException.class
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    public ReactiveClient4xxExceptionHandler(List<Class<? extends Throwable>> exceptionClasses, HttpStatusCode httpStatus) {
        super(exceptionClasses, httpStatus);
    }

    @Override
    protected void doFormatIResponse(Throwable ex) {
        ResponseStatusException e = (ResponseStatusException) ex;
        this.setResponse(IResponse.ofFailure(ResponseEnum.INVALID_PARAM.getCode(), e.getMessage()));
    }
}
