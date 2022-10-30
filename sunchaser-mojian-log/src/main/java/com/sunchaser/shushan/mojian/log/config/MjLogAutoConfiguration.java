package com.sunchaser.shushan.mojian.log.config;

import com.sunchaser.shushan.mojian.log.aspect.MjLogAspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mj log auto configuration
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/10/30
 */
@Configuration
@EnableConfigurationProperties(MjLogProperties.class)
@ConditionalOnWebApplication
@ConditionalOnProperty(name = MjLogProperties.PREFIX + "enable", havingValue = "true", matchIfMissing = true)
public class MjLogAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public MjLogAspect mjLogAspect(MjLogProperties properties) {
        return new MjLogAspect(properties);
    }
}
