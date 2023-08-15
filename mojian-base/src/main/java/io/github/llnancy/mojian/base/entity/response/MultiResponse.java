package io.github.llnancy.mojian.base.entity.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serial;
import java.util.Collection;

/**
 * 多对象响应
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/07/10
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class MultiResponse<T> extends IResponse {

    @Serial
    private static final long serialVersionUID = 556787308052679453L;

    /**
     * 数据域
     */
    private Collection<T> data;

    public MultiResponse() {
    }

    public MultiResponse(Integer code, String msg) {
        super(code, msg);
    }

    public MultiResponse(Integer code, String msg, Collection<T> data) {
        super(code, msg);
        this.data = data;
    }

    public static <T> MultiResponse<T> success(Collection<T> data) {
        return success(SUCCESS, data);
    }

    public static <T> MultiResponse<T> success(IResponse resp, Collection<T> data) {
        return success(resp.getCode(), resp.getMsg(), data);
    }

    public static <T> MultiResponse<T> success(Integer code, String msg, Collection<T> data) {
        return new MultiResponse<>(code, msg, data);
    }
}
