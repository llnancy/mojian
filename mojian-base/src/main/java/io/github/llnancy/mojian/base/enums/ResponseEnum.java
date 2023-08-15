package io.github.llnancy.mojian.base.enums;

import io.github.llnancy.mojian.base.entity.response.Response;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * code/msg enum
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/07/10
 */
@AllArgsConstructor
@Getter
public enum ResponseEnum implements Response {

    /**
     * 内置状态码
     */
    SUCCESS(20000, "请求成功"),
    LOGOUT_SUCCESS(20002, "退出成功"),
    INVALID_PARAM(40000, "参数有误"),
    UNAUTHORIZED(40100, "未经授权"),
    FORBIDDEN(40300, "暂无权限"),
    FAILURE(50000, "请求失败"),
    BAD_CREDENTIALS(50001, "用户名或密码错误"),
    ;

    private final Integer code;

    private final String msg;
}
