package io.github.llnancy.mojian.desensitize.strategy.impl;

import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;

/**
 * email desensitize strategy.
 * only the first letter of the mailbox prefix is displayed, the others are hidden,
 * and the characters after the @ symbol are displayed in full.
 * eg. a****@lilu.org.cn
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/12/23
 */
public class EmailDesensitizeStrategy extends AbstractHeadTailDesensitizeStrategy {

    @Override
    protected int headLength() {
        return 1;
    }

    @Override
    protected int tailLength(@Nonnull String source) {
        return StringUtils.length(source) - StringUtils.indexOf(source, "@");
    }
}
