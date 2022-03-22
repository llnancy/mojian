package com.sunchaser.mojian.base.entity.response;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 多对象分页响应
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/10/24
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MultiPageResponse<T> extends MultiResponse<T> {
    private static final long serialVersionUID = 5074494799013392843L;

    private Long count = 0L;

    private Boolean hasNext = Boolean.TRUE;

    public MultiPageResponse() {
    }

    public MultiPageResponse(Integer resultCode, String resultMsg, Long count, Boolean hasNext) {
        super(resultCode, resultMsg);
        this.count = count;
        this.hasNext = hasNext;
    }

    public MultiPageResponse(Integer resultCode, String resultMsg, Collection<T> data, Long count, Boolean hasNext) {
        super(resultCode, resultMsg, data);
        this.count = count;
        this.hasNext = hasNext;
    }

    public static <T> MultiPageResponse<T> success(Collection<T> data, Long count, Boolean hasNext) {
        return success(IResponse.SUCCESS, data, count, hasNext);
    }

    public static <T> MultiPageResponse<T> success(IResponse resp, Collection<T> data, Long count, Boolean hasNext) {
        return success(resp.getResultCode(), resp.getResultMsg(), data, count, hasNext);
    }

    public static <T> MultiPageResponse<T> success(Integer resultCode, String resultMsg, Collection<T> data, Long count, Boolean hasNext) {
        return new MultiPageResponse<>(resultCode, resultMsg, data, count, hasNext);
    }

    public static <T, R> MultiPageResponse<T> success(Page<R> page, Function<? super R, ? extends T> mapper) {
        List<T> collect = page.getRecords()
                .stream()
                .map(mapper)
                .collect(Collectors.toList());
        return success(collect, page.getTotal(), page.hasNext());
    }

    public static <T> MultiPageResponse<T> failure() {
        return failure(IResponse.FAILURE);
    }

    public static <T> MultiPageResponse<T> failure(IResponse resp) {
        return new MultiPageResponse<>(resp.getResultCode(), resp.getResultMsg(), 0L, Boolean.FALSE);
    }

    public static <T> MultiPageResponse<T> empty() {
        return success(IResponse.SUCCESS, Collections.emptyList(), 0L, Boolean.FALSE);
    }
}
