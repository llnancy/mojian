package io.github.llnancy.mojian.desensitize.core;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.introspect.Annotated;
import io.github.llnancy.mojian.desensitize.annotation.Desensitize;

import java.io.Serial;
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

    @Override
    public Version version() {
        return com.fasterxml.jackson.databind.cfg.PackageVersion.VERSION;
    }

    @Override
    public Object findSerializer(Annotated am) {
        Desensitize desensitize = _findAnnotation(am, Desensitize.class);
        if (Objects.nonNull(desensitize)) {
            return new DesensitizeSerializer(desensitize);
        }
        return super.findSerializer(am);
    }
}
