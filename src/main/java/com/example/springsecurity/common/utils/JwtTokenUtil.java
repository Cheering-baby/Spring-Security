package com.example.springsecurity.common.utils;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
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
               .setClaims(claims)
               .setExpiration(generateExpiration())
               .signWith(SignatureAlgorithm.ES512, secret)
               .compact();
   }

    /**
     * 生成token的过期时间
     */

    private Date generateExpiration() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }
}
