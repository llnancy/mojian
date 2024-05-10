package io.github.llnancy.mojian.web.advice.handler;

import io.github.llnancy.mojian.base.entity.response.IResponse;
import org.springframework.http.HttpStatusCode;

import java.util.List;

/**
 * exception handler
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2024/3/15
 */
public interface IExceptionHandler {

    List<Class<? extends Throwable>> getExceptionClasses();

    HttpStatusCode getStatusCode();

    IResponse getResponse();

    IExceptionHandler formatIResponse(Throwable ex);
}
