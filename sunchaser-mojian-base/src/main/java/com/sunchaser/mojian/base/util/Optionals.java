package com.sunchaser.mojian.base.util;

import org.springframework.beans.BeanUtils;

import java.util.*;

import static com.sunchaser.mojian.base.constants.StringConstants.EMPTY;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/10/26
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

    public static String of(String str) {
        return of(str, EMPTY);
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

    public static <T> T of(T t, Class<T> clazz) {
        return Optional.ofNullable(t).orElseGet(() -> BeanUtils.instantiateClass(clazz));
    }

    public static <T> T of(T t, T defaultValue) {
        return Optional.ofNullable(t).orElse(defaultValue);
    }
}
