package io.github.llnancy.mojian.base.entity.response;

import io.github.llnancy.mojian.base.enums.ResponseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serial;

/**
 * 基本响应对象
 * 提供常见的响应 code、msg
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/07/10
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class IResponse implements Response {

    @Serial
    private static final long serialVersionUID = -804559387002430359L;

    /**
     * 业务状态码
     */
    private Integer code;

    /**
     * 响应消息
     */
    private String msg;

    public static final IResponse SUCCESS = ResponseEnum.SUCCESS.toResponse();

    public static final IResponse INVALID_PARAM = ResponseEnum.INVALID_PARAM.toResponse();

    public static final IResponse FAILURE = ResponseEnum.FAILURE.toResponse();

    public static IResponse of(Response res) {
        return res.toResponse();
    }

    public static IResponse ofSuccess() {
        return SUCCESS;
    }

    public static IResponse ofSuccess(Response res) {
        return of(res);
    }

    public static IResponse ofFailure() {
        return FAILURE;
    }

    public static IResponse ofFailure(Integer code) {
        return ofFailure(code, FAILURE.msg);
    }

    public static IResponse ofFailure(String msg) {
        return ofFailure(FAILURE.code, msg);
    }

    public static IResponse ofFailure(Response res) {
        return of(res);
    }

    public static IResponse ofFailure(Integer code, String msg) {
        return new IResponse(code, msg);
    }
}
