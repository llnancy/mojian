package io.github.llnancy.mojian.desensitize.core;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;
import io.github.llnancy.mojian.desensitize.annotation.Desensitize;
import io.github.llnancy.mojian.desensitize.strategy.DesensitizeStrategy;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

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

    public DesensitizeSerializer(Desensitize desensitize) {
        super(String.class, false);
        Assert.notNull(desensitize, "Desensitize annotation must not be null!");
        this.desensitize = desensitize;
    }

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        Class<? extends DesensitizeStrategy> strategyClazz = desensitize.strategy();
        String ch = desensitize.placeholder();
        if (strategyClazz.isInterface()) {
            gen.writeString(DesensitizeStrategy.DEFAULT_INSTANCE.desensitize(value, ch));
            return;
        }
        DesensitizeStrategy strategy = BeanUtils.instantiateClass(strategyClazz);
        gen.writeString(strategy.desensitize(value, ch));
    }
}
