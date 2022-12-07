package io.github.llnancy.mojian.base.entity.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Collection;

/**
 * 多对象响应
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/10/24
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class MultiResponse<T> extends IResponse {

    private static final long serialVersionUID = 556787308052679453L;

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
