package io.github.llnancy.mojian.desensitize.core;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;
import io.github.llnancy.mojian.desensitize.annotation.Desensitize;
import io.github.llnancy.mojian.desensitize.strategy.DesensitizeStrategy;

import java.io.IOException;
import java.io.Serial;

/**
 * DesensitizeSerializer
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/07/10
 */
@JacksonStdImpl
public class DesensitizeSerializer extends StdScalarSerializer<String> {

    @Serial
    private static final long serialVersionUID = -2722597370008662311L;

    private final Desensitize desensitize;

    private final DesensitizeStrategy desensitizeStrategy;

    public DesensitizeSerializer(Desensitize desensitize, DesensitizeStrategy desensitizeStrategy) {
        super(String.class, false);
        this.desensitize = desensitize;
        this.desensitizeStrategy = desensitizeStrategy;
    }

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        String ch = desensitize.placeholder();
        gen.writeString(desensitizeStrategy.desensitize(value, ch));
    }
}
