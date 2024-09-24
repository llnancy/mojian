package io.github.llnancy.mojian.jwt;

import lombok.Getter;
import lombok.Setter;

/**
 * jwt config
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2024/9/23
 */
@Getter
@Setter
public class JwtConfig {

    /**
     * jwt 过期时间，单位毫秒
     */
    private Long expiration = 24 * 60 * 3600 * 1000L;

    /**
     * jwt 签名类型
     */
    private SignType signType = SignType.SECRET;

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

    /**
     * 签名类型
     */
    public enum SignType {

        /**
         * 不签名
         */
        NONE,

        /**
         * SECRET: 秘钥
         */
        SECRET,

        /**
         * 公私钥
         */
        PUBLIC_PRIVATE_KEY
    }
}
