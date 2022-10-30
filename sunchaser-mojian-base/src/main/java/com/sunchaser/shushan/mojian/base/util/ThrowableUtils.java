package com.sunchaser.shushan.mojian.base.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * throwable util
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/10/30
 */
public final class ThrowableUtils {

    private ThrowableUtils() {
    }

    public static String toString(Throwable t) {
        StringWriter writer = new StringWriter();
        try (PrintWriter pw = new PrintWriter(writer)) {
            t.printStackTrace(pw);
            return writer.toString();
        }
    }
}
