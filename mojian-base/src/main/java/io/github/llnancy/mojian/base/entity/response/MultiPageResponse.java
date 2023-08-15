package io.github.llnancy.mojian.base.entity.response;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serial;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 多对象分页响应
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/07/10
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class MultiPageResponse<T> extends MultiResponse<T> {

    @Serial
    private static final long serialVersionUID = 5074494799013392843L;

    /**
     * 总记录数
     */
    private Long count = 0L;

    /**
     * 是否有下一页
     */
    private Boolean hasNext = Boolean.TRUE;

    public MultiPageResponse() {
    }

    public MultiPageResponse(Integer code, String msg, Long count, Boolean hasNext) {
        super(code, msg);
        this.count = count;
        this.hasNext = hasNext;
    }

    public MultiPageResponse(Integer code, String msg, Collection<T> data, Long count, Boolean hasNext) {
        super(code, msg, data);
        this.count = count;
        this.hasNext = hasNext;
    }

    public static <T> MultiPageResponse<T> success(Collection<T> data, Long count, Boolean hasNext) {
        return success(SUCCESS, data, count, hasNext);
    }

    public static <T> MultiPageResponse<T> success(IResponse resp, Collection<T> data, Long count, Boolean hasNext) {
        return success(resp.getCode(), resp.getMsg(), data, count, hasNext);
    }

    public static <T> MultiPageResponse<T> success(Integer code, String msg, Collection<T> data, Long count, Boolean hasNext) {
        return new MultiPageResponse<>(code, msg, data, count, hasNext);
    }

    public static <T, R> MultiPageResponse<T> success(Page<R> page, Function<? super R, ? extends T> mapper) {
        List<T> collect = page.getRecords()
                .stream()
                .map(mapper)
                .collect(Collectors.toList());
        return success(collect, page.getTotal(), page.hasNext());
    }

    public static <T> MultiPageResponse<T> failure() {
        return failure(FAILURE);
    }

    public static <T> MultiPageResponse<T> failure(IResponse resp) {
        return new MultiPageResponse<>(resp.getCode(), resp.getMsg(), 0L, Boolean.FALSE);
    }

    public static <T> MultiPageResponse<T> empty() {
        return success(SUCCESS, Collections.emptyList(), 0L, Boolean.FALSE);
    }
}
