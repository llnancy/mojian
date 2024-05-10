package io.github.llnancy.mojian.web.advice.handler;

import com.google.common.collect.Lists;
import io.github.llnancy.mojian.base.entity.response.IResponse;
import io.github.llnancy.mojian.base.exception.MjBaseBizException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

/**
 * {@link SQLIntegrityConstraintViolationException}
 * {@link RuntimeException}
 * {@link MjBaseBizException}
 * server 5xx exception handler
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2024/3/15
 */
public class Server5XxExceptionHandler extends AbstractExceptionHandler {

    public Server5XxExceptionHandler() {
        this(
                Lists.newArrayList(
                        SQLIntegrityConstraintViolationException.class,
                        RuntimeException.class,
                        MjBaseBizException.class
                ),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    public Server5XxExceptionHandler(List<Class<? extends Throwable>> exceptionClasses, HttpStatusCode httpStatus) {
        super(exceptionClasses, httpStatus);
    }

    @Override
    protected void doFormatIResponse(Throwable ex) {
        this.setResponse(IResponse.ofFailure());
    }
}