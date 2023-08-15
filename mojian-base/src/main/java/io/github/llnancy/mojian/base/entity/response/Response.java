package io.github.llnancy.mojian.base.entity.response;

import java.io.Serializable;

/**
 * API 响应
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/07/10
 */
public interface Response extends Serializable {

    /**
     * 返回状态码
     *
     * @return code
     */
    Integer getCode();

    /**
     * 返回消息体
     *
     * @return msg
     */
    String getMsg();

    /**
     * 转化成 IResponse 基本响应对象
     *
     * @return IResponse
     */
    default IResponse toResponse() {
        return new IResponse(this.getCode(), this.getMsg());
    }
}
