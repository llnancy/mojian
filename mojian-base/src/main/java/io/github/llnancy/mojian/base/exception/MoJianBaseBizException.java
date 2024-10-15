package io.github.llnancy.mojian.base.exception;

import io.github.llnancy.mojian.base.entity.response.Response;
import io.github.llnancy.mojian.base.enums.ResponseEnum;
import lombok.Getter;

import java.io.Serial;

/**
 * mj base biz exception
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/07/10
 */
@Getter
public class MoJianBaseBizException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -3792616129754882226L;

    /**
     * 异常码
     */
    private Integer code = ResponseEnum.FAILURE.getCode();

    /**
     * use String.format()
     *
     * @param response Response
     * @param args     占位符参数
     */
    public MoJianBaseBizException(Response response, Object... args) {
        super(String.format(response.getMsg(), args));
        this.code = response.getCode();
    }

    public MoJianBaseBizException(Integer code, String message, Object... args) {
        super(String.format(message, args));
        this.code = code;
    }

    public MoJianBaseBizException(String message, Object... args) {
        super(String.format(message, args));
    }

    public MoJianBaseBizException(String message, Throwable cause, Object... args) {
        super(String.format(message, args), cause);
    }

    public MoJianBaseBizException(Throwable cause) {
        super(cause);
    }

    public MoJianBaseBizException() {
    }

}
