package io.github.llnancy.mojian.desensitize.strategy.impl;

import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;

/**
 * abstract implementation of desensitize strategy based on head-tail mode.
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/12/23
 */
public abstract class AbstractHeadTailDesensitizeStrategy extends AbstractDesensitizeStrategy {

    @Override
    protected String doDesensitize(@Nonnull String source, @Nonnull String placeholder) {
        int head = headLength(source);
        int tail = tailLength(source);
        String left = StringUtils.left(source, head);
        String right = StringUtils.right(source, tail);
        String repeat = StringUtils.repeat(placeholder, repeatLength(source));
        return left.concat(repeat).concat(right);
    }

    protected int repeatLength(@Nonnull String source) {
        return StringUtils.length(source) - headLength(source) - tailLength(source);
    }

    protected int headLength(@Nonnull String source) {
        return headLength();
    }

    protected int headLength() {
        return 0;
    }

    protected int tailLength(@Nonnull String source) {
        return tailLength();
    }

    protected int tailLength() {
        return 0;
    }
}
