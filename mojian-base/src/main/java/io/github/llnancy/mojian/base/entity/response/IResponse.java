package io.github.llnancy.mojian.base.entity.response;

import io.github.llnancy.mojian.base.enums.ResponseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 基本响应对象
 * 提供常见的响应code、msg
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/10/24
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class IResponse implements Response {

    private static final long serialVersionUID = -804559387002430359L;

    private Integer code;

    private String msg;

    public static final IResponse SUCCESS = ResponseEnum.SUCCESS.toResponse();

    public static final IResponse INVALID_PARAM = ResponseEnum.INVALID_PARAM.toResponse();

    public static final IResponse FAILURE = ResponseEnum.FAILURE.toResponse();

    public static IResponse ofSuccess() {
        return SUCCESS;
    }

    public static IResponse ofFailure() {
        return FAILURE;
    }

    public static IResponse ofFailure(Integer code, String msg) {
        return new IResponse(code, msg);
    }
}
