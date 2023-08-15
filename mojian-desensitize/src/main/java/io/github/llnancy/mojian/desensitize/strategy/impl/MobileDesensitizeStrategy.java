package io.github.llnancy.mojian.desensitize.strategy.impl;

/**
 * mobile desensitize strategy.
 * only the first three and last four digits are displayed.
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/07/10
 */
public class MobileDesensitizeStrategy extends AbstractHeadTailDesensitizeStrategy {

    @Override
    protected int headLength() {
        return 3;
    }

    @Override
    protected int tailLength() {
        return 4;
    }
}
