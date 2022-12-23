package io.github.llnancy.mojian.desensitize.strategy.impl;

/**
 * address desensitize strategy.
 * only the first six characters are displayed, usually the name of the province and city.
 * eg. 浙江省杭州市****
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/12/23
 */
public class AddressDesensitizeStrategy extends AbstractHeadTailDesensitizeStrategy {

    @Override
    protected int headLength() {
        return 6;
    }
}
