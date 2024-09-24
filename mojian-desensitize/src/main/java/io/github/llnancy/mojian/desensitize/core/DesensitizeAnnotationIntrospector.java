package io.github.llnancy.mojian.desensitize.core;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotationIntrospectorPair;
import io.github.llnancy.mojian.desensitize.annotation.Desensitize;
import io.github.llnancy.mojian.desensitize.strategy.DesensitizeStrategy;

import java.io.Serial;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * DesensitizeAnnotationIntrospector
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/07/10
 */
public class DesensitizeAnnotationIntrospector extends AnnotationIntrospector {

    @Serial
    private static final long serialVersionUID = 3243654293640319216L;

    public static AnnotationIntrospector INSTANCE = new DesensitizeAnnotationIntrospector();

    private static final Map<Class<? extends DesensitizeStrategy>, DesensitizeStrategy> STRATEGY_INSTANCE_CACHE = new HashMap<>(16);

    public static void configureAnnotationIntrospector(ObjectMapper objectMapper) {
        AnnotationIntrospector introspector = objectMapper.getSerializationConfig().getAnnotationIntrospector();
        AnnotationIntrospector pair = AnnotationIntrospectorPair.pair(introspector, INSTANCE);
        objectMapper.setAnnotationIntrospector(pair);
    }

    @Override
    public Version version() {
        return com.fasterxml.jackson.databind.cfg.PackageVersion.VERSION;
    }

    @Override
    public Object findSerializer(Annotated am) {
        Desensitize desensitize = _findAnnotation(am, Desensitize.class);
        if (Objects.nonNull(desensitize)) {
            Class<? extends DesensitizeStrategy> strategy = desensitize.strategy();
            DesensitizeStrategy desensitizeStrategy = STRATEGY_INSTANCE_CACHE.computeIfAbsent(strategy, clazz -> {
                if (strategy.isInterface()) {
                    return DesensitizeStrategy.NOP_INSTANCE;
                } else {
                    return instantiateClass(strategy);
                }
            });
            return new DesensitizeSerializer(desensitize, desensitizeStrategy);
        }
        return super.findSerializer(am);
    }

    private static <T> T instantiateClass(Class<T> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new IllegalStateException("Failed to instantiate " + clazz, e);
        }
    }
}
