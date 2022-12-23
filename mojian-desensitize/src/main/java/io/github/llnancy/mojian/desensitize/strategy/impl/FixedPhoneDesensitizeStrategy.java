package io.github.llnancy.mojian.desensitize.strategy.impl;

/**
 * fixed phone desensitize strategy.
 * only the last four digits are displayed.
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/12/23
 */
public class FixedPhoneDesensitizeStrategy extends AbstractHeadTailDesensitizeStrategy {

    @Override
    protected int tailLength() {
        return 4;
    }
}
