package io.github.llnancy.mojian.desensitize.strategy.impl;

import javax.annotation.Nonnull;

/**
 * all desensitize strategy
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/12/16
 */
public class AllDesensitizeStrategy extends AbstractHeadTailDesensitizeStrategy {

    @Override
    protected int repeatLength(@Nonnull String source) {
        return super.repeatLength(source);
    }

    @Override
    protected int headLength() {
        return super.headLength();
    }

    @Override
    protected int tailLength() {
        return super.tailLength();
    }
}
