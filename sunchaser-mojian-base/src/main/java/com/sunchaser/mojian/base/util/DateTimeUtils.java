package com.sunchaser.mojian.base.util;

import java.time.format.DateTimeFormatter;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/3/13
 */
public class DateTimeUtils {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
}
