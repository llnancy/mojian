package io.github.llnancy.mojian.jwt.boot.autoconfigure;

import io.github.llnancy.mojian.jwt.JwtConfig;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.util.Assert;

/**
 * jwt configurer
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2024/9/23
 */
public class MoJianJwtConfigurer {

    private final MoJianJwtProperties moJianJwtProperties;

    public MoJianJwtConfigurer(MoJianJwtProperties moJianJwtProperties) {
        Assert.notNull(moJianJwtProperties, "JwtProperties must not be null");
        this.moJianJwtProperties = moJianJwtProperties;
    }

    public void configure(JwtConfig jwtConfig) {
        PropertyMapper map = PropertyMapper.get();
        map.from(moJianJwtProperties::getExpiration)
                .whenNonNull()
                .to(jwtConfig::setExpiration);
        map.from(moJianJwtProperties::getSignType)
                .whenInstanceOf(JwtConfig.SignType.class)
                .to(jwtConfig::setSignType);
        map.from(moJianJwtProperties::getSecret)
                .whenHasText()
                .to(jwtConfig::setSecret);
        map.from(moJianJwtProperties::getPublicKeyLocation)
                .whenHasText()
                .to(jwtConfig::setPublicKeyLocation);
        map.from(moJianJwtProperties::getPrivateKeyLocation)
                .whenHasText()
                .to(jwtConfig::setPrivateKeyLocation);
    }
}
