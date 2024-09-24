package io.github.llnancy.mojian.jwt.boot.autoconfigure;

import io.github.llnancy.mojian.jwt.JwtConfig;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * jwt properties
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/07/10
 */
@Getter
@Setter
@ConfigurationProperties(prefix = MoJianJwtProperties.MOJIAN_JWT)
public class MoJianJwtProperties {

    public static final String MOJIAN_JWT = "mojian.jwt";

    /**
     * jwt 过期时间，单位毫秒
     */
    private Long expiration = 24 * 60 * 3600 * 1000L;

    /**
     * jwt 签名类型
     */
    private JwtConfig.SignType signType = JwtConfig.SignType.SECRET;

    /**
     * 签名类型为秘钥时的秘钥
     */
    private String secret;

    /**
     * 签名类型为公私钥时的公钥路径
     */
    private String publicKeyLocation;

    /**
     * 签名类型为公私钥时的私钥路径
     */
    private String privateKeyLocation;
}
