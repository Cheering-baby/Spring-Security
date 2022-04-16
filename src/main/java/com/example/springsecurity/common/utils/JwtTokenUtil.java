package com.example.springsecurity.common.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);
    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * 负责生成jwt的token
     */
   private String generateToken(Map<String, Object> claims) {
       return Jwts.builder()
               .setClaims(claims) //
               .setExpiration(generateExpiration()) // 设置过期时间
               .signWith(SignatureAlgorithm.ES512, secret) // 加密方式
               .compact();
   }

    /**
     * 生成token的过期时间
     */

    private Date generateExpiration() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    /**
     * 从Token获取jwt的负载
     */

    private Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJwt(token)
                    .getBody();
        } catch (Exception e) {
          LOGGER.info("JWT格式验证失败, {}", token);
        }
        return claims;
    }

    /**
     * 从token中获取登录用户名
     */

    public String getUserNameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }

        return username;
    }

    /**
     * 验证token是否失效
     */

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUserNameFromToken(token);
        return userDetails.getUsername().equals(username) && !isTokenExpired(token);
    }

    /**
     * token是否过期
     */

    private boolean isTokenExpired(String token) {
        Date expireDate = getExpireDateFromToken(token);
        return expireDate.before(new Date());
    }

    /**
     * 获取Token的过期时间
     */

    private Date getExpireDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    /**
     * 根据用户信息生成token
     */

    public String generateToken(UserDetails userDetails) {
       HashMap<String, Object> claims = new HashMap<>();
       claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
       claims.put(CLAIM_KEY_CREATED, new Date());
       return generateToken(claims);
    }

}
