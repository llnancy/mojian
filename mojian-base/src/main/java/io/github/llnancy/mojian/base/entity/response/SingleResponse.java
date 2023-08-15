package io.github.llnancy.mojian.base.entity.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serial;

/**
 * 单对象响应
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/07/10
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class SingleResponse<T> extends IResponse {

    @Serial
    private static final long serialVersionUID = -6629737337259306630L;

    /**
     * 数据域
     */
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
