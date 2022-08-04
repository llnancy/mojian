package com.sunchaser.shushan.mojian.base.exception;

/**
 * mojian exception
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/10/19
 */
public class MjException extends RuntimeException {

    private static final long serialVersionUID = -3792616129754882226L;

    public MjException(String message) {
        super(message);
    }

    public MjException(String message, Throwable cause) {
        super(message, cause);
    }

    public MjException(Throwable cause) {
        super(cause);
    }

    public MjException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public MjException() {
    }
}
