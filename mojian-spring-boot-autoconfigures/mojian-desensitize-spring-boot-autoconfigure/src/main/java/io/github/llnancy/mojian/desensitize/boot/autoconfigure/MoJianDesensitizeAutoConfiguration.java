package io.github.llnancy.mojian.desensitize.boot.autoconfigure;

import com.fasterxml.jackson.databind.introspect.AnnotationIntrospectorPair;
import io.github.llnancy.mojian.desensitize.core.DesensitizeAnnotationIntrospector;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;

/**
 * mojian desensitize auto configuration
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2024/9/23
 */
public class MoJianDesensitizeAutoConfiguration {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> builder.annotationIntrospector(annotationIntrospector -> AnnotationIntrospectorPair.pair(annotationIntrospector, DesensitizeAnnotationIntrospector.INSTANCE));
    }
}
