package com.xiang;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;
import java.util.UUID;

@SpringBootTest
class SpringSecurity6ApplicationTests {

    @Test
    public void testJwt() {
        String secret = UUID.randomUUID().toString();
        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        String token = Jwts
                .builder()
                .setExpiration(new Date(
                        System.currentTimeMillis() + Duration.ofMinutes(2).toMillis()
                ))
                .claim("userId","123")
                .claim("name","zhangsan")
                .signWith(secretKey)
                .compact();
        System.out.println(token);


        Jws<Claims> claimsJws = Jwts
                .parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);

        System.out.println(claimsJws.getBody());

    }
}
