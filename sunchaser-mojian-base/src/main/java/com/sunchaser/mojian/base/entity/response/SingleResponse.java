package com.sunchaser.mojian.base.entity.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 单对象响应
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/10/24
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class SingleResponse<T> extends IResponse {

    private static final long serialVersionUID = -6629737337259306630L;

    private T data;

    public SingleResponse() {
    }

    public SingleResponse(Integer resultCode, String resultMsg) {
        super(resultCode, resultMsg);
    }

    public SingleResponse(Integer resultCode, String resultMsg, T data) {
        super(resultCode, resultMsg);
        this.data = data;
    }

    public static <T> SingleResponse<T> success(T t) {
        return success(IResponse.SUCCESS, t);
    }

    public static <T> SingleResponse<T> success(Response resp, T t) {
        return success(resp.getResultCode(), resp.getResultMsg(), t);
    }

    public static <T> SingleResponse<T> success(Integer resultCode, String resultMsg, T t) {
        return new SingleResponse<>(resultCode, resultMsg, t);
    }

    public static <T> SingleResponse<T> failure() {
        return failure(IResponse.FAILURE);
    }

    public static <T> SingleResponse<T> failure(Response resp) {
        return new SingleResponse<>(resp.getResultCode(), resp.getResultMsg());
    }
}
