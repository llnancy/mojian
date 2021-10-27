package com.sunchaser.mojian.base.entity.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 基本响应对象
 * 提供常见的响应code、msg
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/10/24
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class IResponse implements Response {
    private static final long serialVersionUID = -804559387002430359L;

    private Integer resultCode;

    private String resultMsg;

    public static final IResponse SUCCESS = new IResponse(20000, "请求成功");

    public static final IResponse INVALID_PARAM = new IResponse(40000, "参数有误");

    public static final IResponse FAILURE = new IResponse(50000, "请求失败");

    public static IResponse ofSuccess() {
        return SUCCESS;
    }

    public static IResponse ofFailure() {
        return FAILURE;
    }

    public static IResponse ofFailure(Integer resultCode, String resultMsg) {
        return new IResponse(resultCode, resultMsg);
    }
}
