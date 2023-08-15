package io.github.llnancy.mojian.desensitize.strategy.impl;

import io.github.llnancy.mojian.desensitize.strategy.DesensitizeStrategy;
import org.apache.commons.lang3.StringUtils;

/**
 * abstract implementation of desensitize strategy
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/07/10
 */
public abstract class AbstractDesensitizeStrategy implements DesensitizeStrategy {

    @Override
    public String desensitize(String source, String placeholder) {
        if (StringUtils.isNotBlank(source)) {
            return doDesensitize(source, StringUtils.getIfBlank(placeholder, () -> DEFAULT_PLACEHOLDER));
        }
        return DesensitizeStrategy.super.desensitize(source, placeholder);
    }

    protected abstract String doDesensitize(String source, String placeholder);
}
