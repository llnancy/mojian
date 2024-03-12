package io.github.llnancy.mojian.base.util;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Optional util
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/07/10
 */
public class Optionals {

    private Optionals() {
    }

    public static Integer of(Integer i) {
        return of(i, 0);
    }

    public static Long of(Long l) {
        return of(l, 0L);
    }

    public static Double of(Double d) {
        return of(d, 0.0);
    }

    public static String of(String str) {
        return of(str, "");
    }

    public static Boolean ofFalse(Boolean bool) {
        return of(bool, Boolean.FALSE);
    }

    public static Boolean ofTrue(Boolean bool) {
        return of(bool, Boolean.TRUE);
    }

    public static <T> Collection<T> of(Collection<T> col) {
        return Optional.ofNullable(col).orElseGet(Collections::emptyList);
    }

    public static <T> List<T> of(List<T> col) {
        return Optional.ofNullable(col).orElseGet(Collections::emptyList);
    }

    public static <K, V> Map<K, V> of(Map<K, V> map) {
        return Optional.ofNullable(map).orElseGet(Collections::emptyMap);
    }

    public static <T> Set<T> of(Set<T> set) {
        return Optional.ofNullable(set).orElseGet(Collections::emptySet);
    }

    public static <T> T of(T t, T defaultValue) {
        return Optional.ofNullable(t).orElse(defaultValue);
    }
}
