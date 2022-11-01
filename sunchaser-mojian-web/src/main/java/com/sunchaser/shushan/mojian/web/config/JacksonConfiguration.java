package com.sunchaser.shushan.mojian.web.config;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunchaser.shushan.mojian.base.util.JsonUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.text.SimpleDateFormat;

/**
 * Jackson config
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/3/18
 */
public class JacksonConfiguration {

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = JsonUtils.createObjectMapper();
        // 格式化 Date
        objectMapper.setDateFormat(new SimpleDateFormat(DatePattern.NORM_DATETIME_PATTERN));

        // 时间模块：格式化 Java8 的 LocalDateTime
        JsonUtils.configureJava8Time(objectMapper);

        converter.setObjectMapper(objectMapper);
        return converter;
    }
}
