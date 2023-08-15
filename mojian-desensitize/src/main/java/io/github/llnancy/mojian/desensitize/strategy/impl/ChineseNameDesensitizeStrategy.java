package io.github.llnancy.mojian.desensitize.strategy.impl;

/**
 * chinese name desensitize strategy.
 * display only the first Chinese character.
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/07/10
 */
public class ChineseNameDesensitizeStrategy extends AbstractHeadTailDesensitizeStrategy {

    @Override
    protected int repeatLength(String source) {
        return 2;
    }

    @Override
    protected int headLength() {
        return 1;
    }
}
