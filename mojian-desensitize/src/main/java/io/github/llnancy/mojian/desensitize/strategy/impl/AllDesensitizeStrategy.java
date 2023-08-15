package io.github.llnancy.mojian.desensitize.strategy.impl;

/**
 * all desensitize strategy
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/07/10
 */
public class AllDesensitizeStrategy extends AbstractHeadTailDesensitizeStrategy {

    @Override
    protected int repeatLength(String source) {
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
