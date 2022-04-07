package com.sunchaser.mojian.base.entity.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
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

    public MultiResponse(Integer resultCode, String resultMsg) {
        super(resultCode, resultMsg);
    }

    public MultiResponse(Integer resultCode, String resultMsg, Collection<T> data) {
        super(resultCode, resultMsg);
        this.data = data;
    }

    public static <T> MultiResponse<T> success(Collection<T> data) {
        return success(IResponse.SUCCESS, data);
    }

    public static <T> MultiResponse<T> success(IResponse resp, Collection<T> data) {
        return success(resp.getResultCode(), resp.getResultMsg(), data);
    }

    public static <T> MultiResponse<T> success(Integer resultCode, String resultMsg, Collection<T> data) {
        return new MultiResponse<>(resultCode, resultMsg, data);
    }
}
