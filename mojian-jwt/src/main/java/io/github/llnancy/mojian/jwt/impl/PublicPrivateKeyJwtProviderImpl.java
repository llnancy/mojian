package io.github.llnancy.mojian.jwt.impl;

import io.github.llnancy.mojian.jwt.JwtConfig;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * jwt implementation based on public-private key encryption
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/07/10
 */
public class PublicPrivateKeyJwtProviderImpl extends AbstractJwtProvider {

    public PublicPrivateKeyJwtProviderImpl(JwtConfig jwtConfig) {
        super(jwtConfig);
    }

    @Override
    protected String doCreateJwt(JwtBuilder jwtBuilder) {
        return jwtBuilder.signWith(getPrivateKey())
                .compact();
    }

    @Override
    protected JwtParser buildJwtParser() {
        return Jwts.parserBuilder()
                .setSigningKey(getPublicKey())
                .build();
    }

    @SneakyThrows
    private PublicKey getPublicKey() {
        String publicStr = FileUtils.readFileToString(new File(jwtConfig.getPublicKeyLocation()), StandardCharsets.UTF_8);
        byte[] decode = Decoders.BASE64.decode(publicStr);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(decode);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }

    @SneakyThrows
    private PrivateKey getPrivateKey() {
        String privateStr = FileUtils.readFileToString(new File(jwtConfig.getPrivateKeyLocation()), StandardCharsets.UTF_8);
        byte[] decode = Decoders.BASE64.decode(privateStr);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decode);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }
}
