package io.github.llnancy.mojian.desensitize.support;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.AnnotationIntrospectorPair;
import io.github.llnancy.mojian.desensitize.core.DesensitizeAnnotationIntrospector;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;

/**
 * Jackson Desensitize Configuration
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/07/10
 */
public class DesensitizeConfiguration {

    private static final AnnotationIntrospector DESENSITIZE_ANNOTATION_INTROSPECTOR = new DesensitizeAnnotationIntrospector();

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> builder.annotationIntrospector(annotationIntrospector -> AnnotationIntrospectorPair.pair(annotationIntrospector, DESENSITIZE_ANNOTATION_INTROSPECTOR));
    }

    public static ObjectMapper configureAnnotationIntrospector(ObjectMapper objectMapper) {
        AnnotationIntrospector introspector = objectMapper.getSerializationConfig().getAnnotationIntrospector();
        AnnotationIntrospector pair = AnnotationIntrospectorPair.pair(introspector, DESENSITIZE_ANNOTATION_INTROSPECTOR);
        objectMapper.setAnnotationIntrospector(pair);
        return objectMapper;
    }
}
