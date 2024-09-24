package io.github.llnancy.mojian.jwt.impl;

import io.github.llnancy.mojian.jwt.JwtConfig;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;

/**
 * jwt implementation with none sign
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/07/10
 */
public class NoneSignJwtProviderImpl extends AbstractJwtProvider {

    public NoneSignJwtProviderImpl(JwtConfig jwtConfig) {
        super(jwtConfig);
    }

    @Override
    protected String doCreateJwt(JwtBuilder jwtBuilder) {
        return jwtBuilder.compact();
    }

    @Override
    protected JwtParser buildJwtParser() {
        return Jwts.parserBuilder()
                .build();
    }
}
