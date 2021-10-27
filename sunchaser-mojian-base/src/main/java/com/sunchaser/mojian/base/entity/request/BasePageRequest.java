package com.sunchaser.mojian.base.entity.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/10/24
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BasePageRequest extends BaseRequest {
    private static final long serialVersionUID = -7698513373426563684L;

    private Integer pageNo = 1;

    private Integer pageSize = 20;
}
