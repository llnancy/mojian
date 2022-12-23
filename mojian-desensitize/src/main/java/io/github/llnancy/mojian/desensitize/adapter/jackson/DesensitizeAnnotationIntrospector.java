package io.github.llnancy.mojian.desensitize.adapter.jackson;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.introspect.Annotated;
import io.github.llnancy.mojian.desensitize.annotation.Desensitize;

import java.util.Objects;

/**
 * DesensitizeAnnotationIntrospector
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/12/16
 */
public class DesensitizeAnnotationIntrospector extends AnnotationIntrospector {

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
