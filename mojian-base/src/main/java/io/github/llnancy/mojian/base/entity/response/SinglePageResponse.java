package io.github.llnancy.mojian.base.entity.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * 单对象分页响应
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/10/24
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class SinglePageResponse<T> extends SingleResponse<T> {

    private static final long serialVersionUID = 6047231376898018281L;

    private Long count = 0L;

    private Boolean hasNext = Boolean.TRUE;

    public SinglePageResponse() {
    }

    public SinglePageResponse(Integer code, String msg, T data, Long count, Boolean hasNext) {
        super(code, msg, data);
        this.count = count;
        this.hasNext = hasNext;
    }

    public static <T extends Serializable> SinglePageResponse<T> success(T t, Long count, Boolean hasNext) {
        return success(SUCCESS, t, count, hasNext);
    }

    public static <T extends Serializable> SinglePageResponse<T> success(IResponse resp, T t, Long count, Boolean hasNext) {
        return success(resp.getCode(), resp.getMsg(), t, count, hasNext);
    }

    public static <T extends Serializable> SinglePageResponse<T> success(Integer code, String msg, T t, Long count, Boolean hasNext) {
        return new SinglePageResponse<>(code, msg, t, count, hasNext);
    }
}
