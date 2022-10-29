package com.sunchaser.shushan.mojian.base.enums;

import com.sunchaser.shushan.mojian.base.entity.response.IResponse;
import com.sunchaser.shushan.mojian.base.entity.response.Response;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * code/msg enum
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/10/29
 */
@AllArgsConstructor
@Getter
public enum ResponseEnum implements Response {

    /**
     * 内置状态码
     */
    SUCCESS(20000, "请求成功"),
    INVALID_PARAM(40000, "参数有误"),
    FAILURE(50000, "请求失败");

    private final Integer code;

    private final String msg;

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    public IResponse toResponse() {
        return new IResponse(this.code, this.msg);
    }
}
