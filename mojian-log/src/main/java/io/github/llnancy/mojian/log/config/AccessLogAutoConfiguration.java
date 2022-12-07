package io.github.llnancy.mojian.log.config;

import io.github.llnancy.mojian.log.aspect.AccessLogAspect;
import io.github.llnancy.mojian.log.config.property.AccessLogProperties;
import io.github.llnancy.mojian.log.event.AccessLogEventListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * access log auto configuration
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/10/30
 */
@Configuration
@EnableConfigurationProperties(AccessLogProperties.class)
@ConditionalOnWebApplication
@ConditionalOnProperty(name = AccessLogProperties.PREFIX + ".enable", havingValue = "true", matchIfMissing = true)
public class AccessLogAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public AccessLogAspect accessLogAspect(AccessLogProperties properties) {
        return new AccessLogAspect(properties);
    }

    @Bean
    @ConditionalOnMissingBean
    public AccessLogEventListener accessLogEventListener() {
        return new AccessLogEventListener(System.out::println);
    }
}
