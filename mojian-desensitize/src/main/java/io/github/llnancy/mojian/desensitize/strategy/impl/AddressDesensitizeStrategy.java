package io.github.llnancy.mojian.desensitize.strategy.impl;

/**
 * address desensitize strategy.
 * only the first six characters are displayed, usually the name of the province and city.
 * eg. 浙江省杭州市****
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/07/10
 */
public class AddressDesensitizeStrategy extends AbstractHeadTailDesensitizeStrategy {

    @Override
    protected int headLength() {
        return 6;
    }
}
