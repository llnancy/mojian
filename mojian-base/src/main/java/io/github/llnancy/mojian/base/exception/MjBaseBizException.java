package io.github.llnancy.mojian.base.exception;

import io.github.llnancy.mojian.base.entity.response.Response;
import lombok.Getter;

import java.io.Serial;

/**
 * mj base biz exception
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/07/10
 */
@Getter
public class MjBaseBizException extends RuntimeException {

    @Serial
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
        super(String.format(response.getMsg(), args));
        this.code = response.getCode();
    }

    public MjBaseBizException(Integer code, String message, Object... args) {
        super(String.format(message, args));
        this.code = code;
    }

    public MjBaseBizException(String message, Object... args) {
        super(String.format(message, args));
    }

    public MjBaseBizException(String message, Throwable cause, Object... args) {
        super(String.format(message, args), cause);
    }

    public MjBaseBizException(Throwable cause) {
        super(cause);
    }

    public MjBaseBizException() {
    }

}
