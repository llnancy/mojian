package com.sunchaser.mojian.base.entity.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 单对象分页响应
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/10/24
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SinglePageResponse<T extends Serializable> extends SingleResponse<T> {
    private static final long serialVersionUID = 6047231376898018281L;

    private Long count = 0L;

    private Boolean hasNext = Boolean.TRUE;

    public SinglePageResponse() {
    }

    public SinglePageResponse(Integer resultCode, String resultMsg, T data, Long count, Boolean hasNext) {
        super(resultCode, resultMsg, data);
        this.count = count;
        this.hasNext = hasNext;
    }

    public static <T extends Serializable> SinglePageResponse<T> success(T t, Long count, Boolean hasNext) {
        return success(IResponse.SUCCESS, t, count, hasNext);
    }

    public static <T extends Serializable> SinglePageResponse<T> success(IResponse resp, T t, Long count, Boolean hasNext) {
        return success(resp.getResultCode(), resp.getResultMsg(), t, count, hasNext);
    }

    public static <T extends Serializable> SinglePageResponse<T> success(Integer resultCode, String resultMsg, T t, Long count, Boolean hasNext) {
        return new SinglePageResponse<>(resultCode, resultMsg, t, count, hasNext);
    }
}
