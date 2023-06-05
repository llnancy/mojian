package io.github.llnancy.mojian.web.config;

import io.github.llnancy.mojian.web.config.property.JwtProperties;
import io.github.llnancy.mojian.web.jwt.JwtProvider;
import io.github.llnancy.mojian.web.jwt.impl.NoneSignJwtProviderImpl;
import io.github.llnancy.mojian.web.jwt.impl.PublicPrivateKeyJwtProviderImpl;
import io.github.llnancy.mojian.web.jwt.impl.SecretJwtProviderImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * JwtProvider configuration
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/3
 */
@EnableConfigurationProperties({JwtProperties.class})
@ConditionalOnProperty(prefix = JwtProperties.MOJIAN_JWT, value = "enabled", matchIfMissing = true)
public class JwtProviderAutoConfiguration {

    /**
     * zyj.jwt.sign-type 中只能用短横线，不能用驼峰
     */
    public static final String JWT_SIGN_TYPE = JwtProperties.MOJIAN_JWT + ".sign-type";

    @Bean
    @ConditionalOnProperty(name = JWT_SIGN_TYPE, havingValue = "NONE")
    public JwtProvider noneSignJwtService(JwtProperties properties) {
        return new NoneSignJwtProviderImpl(properties);
    }

    @Bean
    @ConditionalOnProperty(name = JWT_SIGN_TYPE, havingValue = "SECRET")
    public JwtProvider secretJwtService(JwtProperties properties) {
        return new SecretJwtProviderImpl(properties);
    }

    @Bean
    @ConditionalOnProperty(name = JWT_SIGN_TYPE, havingValue = "PUBLIC_PRIVATE_KEY")
    public JwtProvider publicPrivateKeyJwtService(JwtProperties properties) {
        return new PublicPrivateKeyJwtProviderImpl(properties);
    }
}
