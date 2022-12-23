package io.github.llnancy.mojian.desensitize.strategy.impl;

import io.github.llnancy.mojian.desensitize.strategy.DesensitizeStrategy;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;

/**
 * abstract implementation of desensitize strategy
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/12/23
 */
public abstract class AbstractDesensitizeStrategy implements DesensitizeStrategy {

    @Override
    public String desensitize(String source, String placeholder) {
        if (StringUtils.isNotBlank(source)) {
            return doDesensitize(source, StringUtils.getIfBlank(placeholder, () -> DEFAULT_PLACEHOLDER));
        }
        return DesensitizeStrategy.super.desensitize(source, placeholder);
    }

    protected abstract String doDesensitize(@Nonnull String source, @Nonnull String placeholder);
}
