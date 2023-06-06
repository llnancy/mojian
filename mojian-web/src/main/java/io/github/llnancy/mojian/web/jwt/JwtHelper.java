package io.github.llnancy.mojian.web.jwt;

import cn.hutool.core.io.FileUtil;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * jwt helper
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/6/6
 */
public class JwtHelper {

    public static void generateRsaKeyFile(File pukFile, File prkFile) {
        KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.RS256);
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        String publicBase64 = Encoders.BASE64.encode(publicKey.getEncoded());
        String privateBase64 = Encoders.BASE64.encode(privateKey.getEncoded());
        FileUtil.writeString(publicBase64, pukFile, StandardCharsets.UTF_8);
        FileUtil.writeString(privateBase64, prkFile, StandardCharsets.UTF_8);
    }

    public static String generateSecret() {
        // 创建随机安全密钥
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        // base64 编码保存
        return Encoders.BASE64.encode(key.getEncoded());
    }
}
