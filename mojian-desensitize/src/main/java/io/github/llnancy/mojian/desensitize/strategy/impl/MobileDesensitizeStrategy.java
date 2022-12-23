package io.github.llnancy.mojian.desensitize.strategy.impl;

/**
 * mobile desensitize strategy.
 * only the first three and last four digits are displayed.
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/12/23
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
