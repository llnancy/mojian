package io.github.llnancy.mojian.jwt;

import io.jsonwebtoken.Claims;

/**
 * jwt service
 * -------------------------------
 * JSON Web Token Service
 * Standard Claims:
 * iss：发行人
 * exp：到期时间
 * sub：主题
 * aud：用户
 * nbf：在此之前不可用
 * iat：发布时间
 * jti：JWT ID 用于标识该 JWT
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/07/10
 */
public interface JwtProvider {

    /**
     * 创建 JWT
     *
     * @param subject 主题（用户名）
     * @return JWT
     */
    String createJwt(String subject);

    /**
     * 解析 JWT
     *
     * @param jwt JWT
     * @return Claims
     */
    Claims parseJwt(String jwt);
}
