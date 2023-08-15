package io.github.llnancy.mojian.desensitize.strategy.impl;

/**
 * id card desensitize strategy.
 * only the last four digits are displayed.
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/07/10
 */
public class IdCardDesensitizeStrategy extends AbstractHeadTailDesensitizeStrategy {

    @Override
    protected int tailLength() {
        return 4;
    }
}
