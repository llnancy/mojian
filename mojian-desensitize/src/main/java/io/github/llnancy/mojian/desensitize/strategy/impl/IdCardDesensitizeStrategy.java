package io.github.llnancy.mojian.desensitize.strategy.impl;

/**
 * id card desensitize strategy.
 * only the last four digits are displayed.
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/12/23
 */
public class IdCardDesensitizeStrategy extends AbstractHeadTailDesensitizeStrategy {

    @Override
    protected int tailLength() {
        return 4;
    }
}
