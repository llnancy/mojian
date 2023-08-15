package io.github.llnancy.mojian.desensitize.strategy.impl;

import org.apache.commons.lang3.StringUtils;

/**
 * abstract implementation of desensitize strategy based on head-tail mode.
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/07/10
 */
public abstract class AbstractHeadTailDesensitizeStrategy extends AbstractDesensitizeStrategy {

    @Override
    protected String doDesensitize(String source, String placeholder) {
        int head = headLength(source);
        int tail = tailLength(source);
        String left = StringUtils.left(source, head);
        String right = StringUtils.right(source, tail);
        String repeat = StringUtils.repeat(placeholder, repeatLength(source));
        return left.concat(repeat).concat(right);
    }

    protected int repeatLength(String source) {
        return StringUtils.length(source) - headLength(source) - tailLength(source);
    }

    protected int headLength(String source) {
        return headLength();
    }

    protected int headLength() {
        return 0;
    }

    protected int tailLength(String source) {
        return tailLength();
    }

    protected int tailLength() {
        return 0;
    }
}
