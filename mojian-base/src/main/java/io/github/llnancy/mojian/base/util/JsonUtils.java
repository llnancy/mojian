package io.github.llnancy.mojian.base.util;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
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
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

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

    public static ObjectMapper commonInit(ObjectMapper objectMapper) {
        // 忽略空 bean 转对象异常
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 忽略在 json 字符串中存在，但是在 Java 类中不存在对应属性时抛出的异常
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 解析含有结束语控制字符(如：ASCII < 32，包含制表符 \t、换行符 \n 和回车 \r)
        objectMapper.configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true);
        // 配置使用 Java8 的 LocalDateTime 时间模块，避免序列化和反序列化出错
        configureJava8Time(objectMapper);
        return objectMapper;
    }

    public static void configureJava8Time(ObjectMapper objectMapper) {
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DatePattern.NORM_DATETIME_FORMATTER));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DatePattern.NORM_DATE_FORMATTER));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DatePattern.NORM_TIME_FORMATTER));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DatePattern.NORM_DATETIME_FORMATTER));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DatePattern.NORM_DATE_FORMATTER));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DatePattern.NORM_TIME_FORMATTER));
        objectMapper.registerModule(javaTimeModule);
    }

    public static ObjectMapper createObjectMapper() {
        return new ObjectMapper();
    }

    public static void withShakeCase(ObjectMapper objectMapper) {
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
    }

    public static void withoutNull(ObjectMapper objectMapper) {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static <T> T toObject(String json, Class<T> clazz) {
        return doToObject(json, clazz, COMMON_MAPPER);
    }

    private static <T> T doToObject(String json, Class<T> clazz, ObjectMapper objectMapper) {
        try {
            if (StringUtils.isBlank(json)) {
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
            if (StringUtils.isBlank(json)) {
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
            if (StringUtils.isBlank(json)) {
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
            if (StringUtils.isBlank(json)) {
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
            if (StringUtils.isBlank(json)) {
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

    public static String doToJsonString(Object object, ObjectMapper objectMapper) {
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
