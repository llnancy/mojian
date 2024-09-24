package io.github.llnancy.mojian.web.advice.handler;

import com.google.common.collect.Lists;
import io.github.llnancy.mojian.base.entity.response.IResponse;
import io.github.llnancy.mojian.base.enums.ResponseEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.AuthenticationException;

import java.util.List;

/**
 * {@link AuthenticationException} handler
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2024/3/17
 */
public class AuthenticationExceptionHandler extends AbstractExceptionHandler {

    public AuthenticationExceptionHandler() {
        this(Lists.newArrayList(AuthenticationException.class), HttpStatus.UNAUTHORIZED);
    }

    public AuthenticationExceptionHandler(List<Class<? extends Throwable>> exceptionClasses, HttpStatusCode httpStatus) {
        super(exceptionClasses, httpStatus);
    }

    @Override
    protected void doFormatIResponse(Throwable ex) {
        this.setResponse(IResponse.ofFailure(ResponseEnum.UNAUTHORIZED.getCode(), ex.getMessage()));
    }
}
