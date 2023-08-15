package io.github.llnancy.mojian.base.util;

import java.util.UUID;

/**
 * ID util
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/07/10
 */
public class IdUtils {

    private IdUtils() {
    }

    public static String randomUUID() {
        return UUID.randomUUID().toString();
    }

    public static String simpleUUID() {
        return randomUUID().replaceAll("-", "");
    }

    public static String simpleUUIDWithSuffix(String suffix) {
        return simpleUUID() + suffix;
    }
}
