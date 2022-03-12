package com.sunchaser.mojian.base.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**
 * Json工具类
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/3/13
 */
public class JsonUtils {
    private static final ObjectMapper COMMON_MAPPER = createObjectMapper();
    private static final ObjectMapper WITH_SHAKE_CASE_MAPPER = createObjectMapper();
    private static final ObjectMapper WITHOUT_NULL_MAPPER = createObjectMapper();

    static {
        commonInit(COMMON_MAPPER);
        withShakeCase(commonInit(WITH_SHAKE_CASE_MAPPER));
        withoutNull(commonInit(WITHOUT_NULL_MAPPER));
    }

    private static ObjectMapper commonInit(ObjectMapper objectMapper) {
        // 忽略空bean转对象异常
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 忽略在json字符串中存在，但是在Java类中不存在对应属性时抛出的异常
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 配置使用Java8的LocalDateTime时间模块，避免序列化和反序列化出错
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeUtils.DATE_TIME_FORMATTER));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeUtils.DATE_FORMATTER));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeUtils.TIME_FORMATTER));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeUtils.DATE_TIME_FORMATTER));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeUtils.DATE_FORMATTER));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeUtils.TIME_FORMATTER));
        objectMapper.registerModule(javaTimeModule);
        return objectMapper;
    }

    private static ObjectMapper createObjectMapper() {
        return new ObjectMapper();
    }

    private static void withShakeCase(ObjectMapper objectMapper) {
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
    }

    private static void withoutNull(ObjectMapper objectMapper) {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static <T> T toObject(String json, Class<T> clazz) {
        return doToObject(json, clazz, COMMON_MAPPER);
    }

    private static <T> T doToObject(String json, Class<T> clazz, ObjectMapper objectMapper) {
        try {
            if (StringUtils.isEmpty(json)) {
                return null;
            }
            checkClass(clazz);
            if (clazz == String.class) {
                @SuppressWarnings("unchecked")
                T res = (T) json;
                return res;
            }
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new JsonException(e);
        }
    }

    public static <T> T toObjectWithShakeCase(String json, Class<T> clazz) {
        return doToObject(json, clazz, WITH_SHAKE_CASE_MAPPER);
    }

    public static <T> T toObject(String json, TypeReference<T> typeReference) {
        try {
            if (StringUtils.isEmpty(json)) {
                return null;
            }
            Preconditions.checkArgument(typeReference != null, "typeReference must not be null");
            return COMMON_MAPPER.readValue(json, typeReference);
        } catch (Exception e) {
            throw new JsonException(e);
        }
    }

    public static <T> List<T> toList(String json, Class<T> clazz) {
        try {
            if (StringUtils.isEmpty(json)) {
                return Collections.emptyList();
            }
            checkClass(clazz);
            JavaType type = COMMON_MAPPER.getTypeFactory().constructCollectionType(List.class, clazz);
            return COMMON_MAPPER.readValue(json, type);
        } catch (Exception e) {
            throw new JsonException(e);
        }
    }

    public static <K, V> Map<K, V> toMap(String json, Class<K> keyClass, Class<V> valueClass) {
        try {
            if (StringUtils.isEmpty(json)) {
                return Collections.emptyMap();
            }
            checkClass(keyClass, "key class must not be null");
            checkClass(valueClass, "value class must not be null");
            JavaType type = COMMON_MAPPER.getTypeFactory().constructParametricType(Map.class, keyClass, valueClass);
            return COMMON_MAPPER.readValue(json, type);
        } catch (Exception e) {
            throw new JsonException(e);
        }
    }

    public static <T> Set<T> toSet(String json, Class<T> clazz) {
        try {
            if (StringUtils.isEmpty(json)) {
                return Collections.emptySet();
            }
            checkClass(clazz);
            JavaType type = COMMON_MAPPER.getTypeFactory().constructCollectionType(Set.class, clazz);
            return COMMON_MAPPER.readValue(json, type);
        } catch (Exception e) {
            throw new JsonException(e);
        }
    }

    public static String toJsonString(Object object) {
        return doToJsonString(object, COMMON_MAPPER);
    }

    private static String doToJsonString(Object object, ObjectMapper objectMapper) {
        if (Objects.isNull(object)) {
            return StringUtils.EMPTY;
        }
        if (object instanceof String) {
            return (String) object;
        }
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new JsonException(e);
        }
    }

    public static String toJsonStringWithoutNull(Object object) {
        return doToJsonString(object, WITHOUT_NULL_MAPPER);
    }

    private static <T> void checkClass(Class<T> clazz) {
        checkClass(clazz, "clazz must not be null!");
    }

    private static <T> void checkClass(Class<T> clazz, String errorMessage) {
        Preconditions.checkArgument(clazz != null, errorMessage);
    }

    public static class JsonException extends RuntimeException {
        private static final long serialVersionUID = 530060844406057111L;

        public JsonException(Throwable cause) {
            super(cause);
        }
    }
}
