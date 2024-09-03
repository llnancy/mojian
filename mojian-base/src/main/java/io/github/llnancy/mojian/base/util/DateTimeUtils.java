package io.github.llnancy.mojian.base.util;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * date-time utils
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2024/8/3
 */
public class DateTimeUtils {

    public static final DateTimeFormatter NORM_DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            .withZone(ZoneId.systemDefault());

    public static final DateTimeFormatter NORM_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault())
            .withZone(ZoneId.systemDefault());

    public static final DateTimeFormatter NORM_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.getDefault())
            .withZone(ZoneId.systemDefault());
}
