package com.rb.common.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.Map;

/**
 * JWT 工具类（支持HS256/RS256算法）
 */
@Slf4j
@Data
public class JwtUtil {

    /**
     * 签名算法类型
     */
    public enum AlgorithmType {
        HS256,
        RS256
    }

    // 配置参数（可通过@Value从配置文件读取）
    @Value("${rb.jwt.secret:default-secret-key}")
    private String secret;

    @Value("${rb.jwt.expiration:3600}")
    private Long expiration;

    @Value("${rb.jwt.algorithm:HS256}")
    private AlgorithmType algorithm;

    // RSA密钥对（RS256算法时需要）
    private KeyPair rsaKeyPair;

    /**
     * 生成令牌
     */
    public String generateToken(Map<String, Object> claims, String subject) {
        final Date now = new Date();
        final Date expireDate = new Date(now.getTime() + expiration * 1000);

        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expireDate);

        switch (algorithm) {
            case HS256:
                builder.signWith(getHs256SecretKey(), SignatureAlgorithm.HS256);
                break;
            case RS256:
                builder.signWith(getRs256PrivateKey(), SignatureAlgorithm.RS256);
                break;
            default:
                throw new IllegalArgumentException("Unsupported algorithm: " + algorithm);
        }

        return builder.compact();
    }

    /**
     * 解析令牌
     */
    public Claims parseToken(String token) {
        try {
            JwtParserBuilder parserBuilder = Jwts.parserBuilder();

            switch (algorithm) {
                case HS256:
                    parserBuilder.setSigningKey(getHs256SecretKey());
                    break;
                case RS256:
                    parserBuilder.setSigningKey(getRs256PublicKey());
                    break;
            }

            return parserBuilder.build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            log.error("JWT expired: {}", e.getMessage());
            throw new JwtException("Token expired");
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
            throw new JwtException("Invalid token signature");
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            throw new JwtException("Invalid token format");
        } catch (Exception e) {
            log.error("JWT validation failed: {}", e.getMessage());
            throw new JwtException("Token validation failed");
        }
    }

    /**
     * 刷新令牌
     */
    public String refreshToken(String token) {
        Claims claims = parseToken(token);
        claims.setIssuedAt(new Date());
        claims.setExpiration(new Date(System.currentTimeMillis() + expiration * 1000));
        return generateToken(claims, claims.getSubject());
    }

    /**
     * 验证令牌是否有效
     */
    public boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    // ========== 密钥相关方法 ==========

    private SecretKey getHs256SecretKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    private PrivateKey getRs256PrivateKey() {
        return rsaKeyPair.getPrivate();
    }

    private PublicKey getRs256PublicKey() {
        return rsaKeyPair.getPublic();
    }
}