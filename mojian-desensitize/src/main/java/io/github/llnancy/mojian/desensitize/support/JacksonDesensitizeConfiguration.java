package io.github.llnancy.mojian.desensitize.support;

import com.fasterxml.jackson.databind.introspect.AnnotationIntrospectorPair;
import io.github.llnancy.mojian.desensitize.adapter.jackson.DesensitizeAnnotationIntrospector;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;

/**
 * Jackson Desensitize Configuration
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/12/23
 */
public class JacksonDesensitizeConfiguration {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> builder.annotationIntrospector(annotationIntrospector -> AnnotationIntrospectorPair.pair(annotationIntrospector, new DesensitizeAnnotationIntrospector()));
    }
}
