package io.github.llnancy.mojian.desensitize.strategy.impl;

/**
 * bankcard desensitize strategy.
 * only the first six and last four digits are displayed.
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/12/23
 */
public class BankCardDesensitizeStrategy extends AbstractHeadTailDesensitizeStrategy {

    @Override
    protected int headLength() {
        return 6;
    }

    @Override
    protected int tailLength() {
        return 4;
    }
}
