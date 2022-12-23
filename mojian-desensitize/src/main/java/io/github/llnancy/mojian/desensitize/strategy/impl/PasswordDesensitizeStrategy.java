package io.github.llnancy.mojian.desensitize.strategy.impl;

import javax.annotation.Nonnull;

/**
 * password desensitize strategy.
 * only six placeholder are displayed.
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/12/23
 */
public class PasswordDesensitizeStrategy extends AbstractHeadTailDesensitizeStrategy {

    @Override
    protected int repeatLength(@Nonnull String source) {
        return 6;
    }
}
