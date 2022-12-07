package io.github.llnancy.mojian.base.entity.response;

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

    public SingleResponse(Integer code, String msg) {
        super(code, msg);
    }

    public SingleResponse(Integer code, String msg, T data) {
        super(code, msg);
        this.data = data;
    }

    public static <T> SingleResponse<T> success(T t) {
        return success(SUCCESS, t);
    }

    public static <T> SingleResponse<T> success(Response resp, T t) {
        return success(resp.getCode(), resp.getMsg(), t);
    }

    public static <T> SingleResponse<T> success(Integer code, String msg, T t) {
        return new SingleResponse<>(code, msg, t);
    }

    public static <T> SingleResponse<T> failure() {
        return failure(FAILURE);
    }

    public static <T> SingleResponse<T> failure(Response resp) {
        return new SingleResponse<>(resp.getCode(), resp.getMsg());
    }
}
