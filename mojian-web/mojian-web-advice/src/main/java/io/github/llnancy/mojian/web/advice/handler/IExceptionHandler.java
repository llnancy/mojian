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

    /**
     * 获取处理器支持处理的异常列表
     *
     * @return list of Throwable
     */
    List<Class<? extends Throwable>> getExceptionClasses();

    /**
     * 获取友好返回的 http 状态码
     *
     * @return {@link HttpStatusCode}
     */
    HttpStatusCode getStatusCode();

    /**
     * 获取响应对象
     *
     * @return {@link IResponse}
     */
    IResponse getResponse();

    /**
     * 从异常中格式化响应对象
     *
     * @param ex the caught exception
     */
    void formatIResponse(Throwable ex);
}
