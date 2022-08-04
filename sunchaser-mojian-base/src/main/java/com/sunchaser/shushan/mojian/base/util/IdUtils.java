package com.sunchaser.shushan.mojian.base.util;

import cn.hutool.core.text.StrPool;

import java.util.UUID;

import static cn.hutool.core.text.CharSequenceUtil.EMPTY;

/**
 * ID util
 *
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
        return randomUUID().replaceAll(StrPool.DASHED, EMPTY);
    }

    public static String simpleUUIDWithSuffix(String suffix) {
        return simpleUUID() + suffix;
    }
}
