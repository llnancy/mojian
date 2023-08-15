package io.github.llnancy.mojian.desensitize.strategy.impl;

import org.apache.commons.lang3.StringUtils;

/**
 * email desensitize strategy.
 * only the first letter of the mailbox prefix is displayed, the others are hidden,
 * and the characters after the @ symbol are displayed in full.
 * eg. a****@lilu.org.cn
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/07/10
 */
public class EmailDesensitizeStrategy extends AbstractHeadTailDesensitizeStrategy {

    @Override
    protected int headLength() {
        return 1;
    }

    @Override
    protected int tailLength(String source) {
        return StringUtils.length(source) - StringUtils.indexOf(source, "@");
    }
}
