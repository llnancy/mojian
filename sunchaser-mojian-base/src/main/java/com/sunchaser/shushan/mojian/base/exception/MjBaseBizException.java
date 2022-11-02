package com.sunchaser.shushan.mojian.base.exception;

import cn.hutool.core.text.StrFormatter;
import com.sunchaser.shushan.mojian.base.entity.response.Response;

/**
 * mj base biz exception
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/10/19
 */
public class MjBaseBizException extends RuntimeException {

    private static final long serialVersionUID = -3792616129754882226L;

    /**
     * 异常码
     */
    private Integer code;

    /**
     * 可使用占位符 {}
     *
     * @param response Response
     * @param args     占位符参数
     */
    public MjBaseBizException(Response response, Object... args) {
        super(StrFormatter.format(response.getMsg(), args));
        this.code = response.getCode();
    }

    public MjBaseBizException(Integer code, String message, Object... args) {
        super(StrFormatter.format(message, args));
        this.code = code;
    }

    public MjBaseBizException(String message, Object... args) {
        super(StrFormatter.format(message, args));
    }

    public MjBaseBizException(String message, Throwable cause, Object... args) {
        super(StrFormatter.format(message, args), cause);
    }

    public MjBaseBizException(Throwable cause) {
        super(cause);
    }

    public MjBaseBizException() {
    }

    public Integer getCode() {
        return code;
    }
}
