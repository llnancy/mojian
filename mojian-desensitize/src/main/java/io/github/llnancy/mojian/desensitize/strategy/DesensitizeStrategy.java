package io.github.llnancy.mojian.desensitize.strategy;

/**
 * desensitize strategy
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/07/10
 */
public interface DesensitizeStrategy {

    String DEFAULT_PLACEHOLDER = "*";

    DesensitizeStrategy DEFAULT_INSTANCE = new DesensitizeStrategy() {
    };

    default String desensitize(String source) {
        return desensitize(source, DEFAULT_PLACEHOLDER);
    }

    default String desensitize(String source, String placeholder) {
        return source;
    }
}
