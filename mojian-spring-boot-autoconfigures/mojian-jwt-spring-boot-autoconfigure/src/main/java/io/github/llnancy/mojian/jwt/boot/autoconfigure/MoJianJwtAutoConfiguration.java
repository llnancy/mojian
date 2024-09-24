package io.github.llnancy.mojian.jwt.boot.autoconfigure;

import io.github.llnancy.mojian.jwt.JwtConfig;
import io.github.llnancy.mojian.jwt.JwtProvider;
import io.github.llnancy.mojian.jwt.impl.NoneSignJwtProviderImpl;
import io.github.llnancy.mojian.jwt.impl.PublicPrivateKeyJwtProviderImpl;
import io.github.llnancy.mojian.jwt.impl.SecretJwtProviderImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * JwtProvider configuration
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/07/10
 */
@EnableConfigurationProperties({MoJianJwtProperties.class})
@ConditionalOnProperty(prefix = MoJianJwtProperties.MOJIAN_JWT, value = "enabled", matchIfMissing = true)
public class MoJianJwtAutoConfiguration {

    /**
     * mojian.jwt.sign-type 中只能用短横线，不能用驼峰
     */
    public static final String JWT_SIGN_TYPE = MoJianJwtProperties.MOJIAN_JWT + ".sign-type";

    @Bean
    @ConditionalOnProperty(name = JWT_SIGN_TYPE, havingValue = "NONE")
    public JwtProvider noneSignJwtService(MoJianJwtProperties properties) {
        return new NoneSignJwtProviderImpl(configureJwtConfig(properties));
    }

    @Bean
    @ConditionalOnProperty(name = JWT_SIGN_TYPE, havingValue = "SECRET")
    public JwtProvider secretJwtService(MoJianJwtProperties properties) {
        return new SecretJwtProviderImpl(configureJwtConfig(properties));
    }

    @Bean
    @ConditionalOnProperty(name = JWT_SIGN_TYPE, havingValue = "PUBLIC_PRIVATE_KEY")
    public JwtProvider publicPrivateKeyJwtService(MoJianJwtProperties properties) {
        return new PublicPrivateKeyJwtProviderImpl(configureJwtConfig(properties));
    }

    private JwtConfig configureJwtConfig(MoJianJwtProperties properties) {
        JwtConfig jwtConfig = new JwtConfig();
        MoJianJwtConfigurer configurer = new MoJianJwtConfigurer(properties);
        configurer.configure(jwtConfig);
        return jwtConfig;
    }
}
