package io.github.llnancy.mojian.jwt.impl;

import io.github.llnancy.mojian.jwt.JwtConfig;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

/**
 * jwt implementation based on secret encryption
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/07/10
 */
public class SecretJwtProviderImpl extends AbstractJwtProvider {

    public SecretJwtProviderImpl(JwtConfig jwtConfig) {
        super(jwtConfig);
    }

    @Override
    protected String doCreateJwt(JwtBuilder jwtBuilder) {
        return jwtBuilder.signWith(getSecretKey())
                .compact();
    }

    @Override
    protected JwtParser buildJwtParser() {
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build();
    }

    private Key getSecretKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtConfig.getSecret()));
    }
}
