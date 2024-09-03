package io.github.llnancy.mojian.web.jwt;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.io.FileUtils;

import javax.crypto.SecretKey;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * jwt helper
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/07/10
 */
public class JwtHelper {

    public static void generateRsaKeyFile(File pukFile, File prkFile) throws IOException {
        KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.RS256);
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        String publicBase64 = Encoders.BASE64.encode(publicKey.getEncoded());
        String privateBase64 = Encoders.BASE64.encode(privateKey.getEncoded());
        FileUtils.write(pukFile, publicBase64, StandardCharsets.UTF_8);
        FileUtils.write(prkFile, privateBase64, StandardCharsets.UTF_8);
    }

    public static String generateSecret() {
        // 创建随机安全密钥
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        // base64 编码保存
        return Encoders.BASE64.encode(key.getEncoded());
    }
}
