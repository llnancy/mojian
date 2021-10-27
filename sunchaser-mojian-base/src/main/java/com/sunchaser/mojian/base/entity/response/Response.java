package com.sunchaser.mojian.base.entity.response;

import java.io.Serializable;

/**
 * API响应
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/10/18
 */
public interface Response extends Serializable {

    Integer getResultCode();

    String getResultMsg();

}
