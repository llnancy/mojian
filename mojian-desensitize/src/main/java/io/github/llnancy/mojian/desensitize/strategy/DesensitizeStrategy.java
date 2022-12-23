package io.github.llnancy.mojian.desensitize.strategy;

/**
 * desensitize strategy
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/12/16
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
