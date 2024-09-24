package io.github.llnancy.mojian.web.time.formatter.config;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import io.github.llnancy.mojian.base.util.DateTimeUtils;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Jackson config
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/07/10
 */
public class JacksonConfiguration {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> builder.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeUtils.NORM_DATETIME_FORMATTER))
                .serializerByType(LocalDate.class, new LocalDateSerializer(DateTimeUtils.NORM_DATE_FORMATTER))
                .serializerByType(LocalTime.class, new LocalTimeSerializer(DateTimeUtils.NORM_TIME_FORMATTER))
                .deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeUtils.NORM_DATETIME_FORMATTER))
                .deserializerByType(LocalDate.class, new LocalDateDeserializer(DateTimeUtils.NORM_DATE_FORMATTER))
                .deserializerByType(LocalTime.class, new LocalTimeDeserializer(DateTimeUtils.NORM_TIME_FORMATTER));
    }
}
