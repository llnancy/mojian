package com.sunchaser.mojian.util;

import cn.hutool.core.text.CharPool;

import java.util.UUID;

import static com.sunchaser.mojian.base.Constants.StringConstants.DASH;
import static com.sunchaser.mojian.base.Constants.StringConstants.EMPTY;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/10/23
 */
public class IdUtils {
    private IdUtils() {
    }

    public static String randomUUID() {
        return UUID.randomUUID().toString();
    }

    public static String simpleUUID() {
        return randomUUID().replaceAll(DASH, EMPTY);
    }

    public static String simpleUUIDWithSuffix(String suffix) {
        return simpleUUID() + suffix;
    }
}
