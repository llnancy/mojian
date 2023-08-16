package io.github.llnancy.mojian.base.entity.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * base page request
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/07/10
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BasePageRequest extends BaseRequest {

    @Serial
    private static final long serialVersionUID = -7698513373426563684L;

    /**
     * 当前页码
     */
    private Integer pageNo = 1;

    /**
     * 每页大小
     */
    private Integer pageSize = 20;

    /**
     * 计算偏移量
     *
     * @return offset
     */
    public Integer getOffset() {
        return (this.pageNo - 1) * pageSize;
    }
}
