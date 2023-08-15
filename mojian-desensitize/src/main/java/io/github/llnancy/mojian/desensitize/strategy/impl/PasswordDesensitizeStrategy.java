package io.github.llnancy.mojian.desensitize.strategy.impl;

/**
 * password desensitize strategy.
 * only six placeholder are displayed.
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/07/10
 */
public class PasswordDesensitizeStrategy extends AbstractHeadTailDesensitizeStrategy {

    @Override
    protected int repeatLength(String source) {
        return 6;
    }
}
