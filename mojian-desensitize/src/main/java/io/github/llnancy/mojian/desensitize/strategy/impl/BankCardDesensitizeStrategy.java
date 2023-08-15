package io.github.llnancy.mojian.desensitize.strategy.impl;

/**
 * bankcard desensitize strategy.
 * only the first six and last four digits are displayed.
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/07/10
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
