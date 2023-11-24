package com.xiang.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;
import java.util.UUID;

@Component
public class FrameworkJwtSecurityContextRepository implements SecurityContextRepository {
    private final SecretKey secretKey = Keys.hmacShaKeyFor(
            UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8)
    );

    private final JwtParser PARSER = Jwts.parserBuilder().setSigningKey(secretKey).build();
    @Override
    public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
        HttpServletRequest request = requestResponseHolder.getRequest();
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        if (StringUtils.isBlank(authorization)) {
            return securityContext;
        }
        Jws<Claims> claimsJws = null;
        try {
            //可能会解析失败
            claimsJws = PARSER.parseClaimsJws(authorization);
        } catch (ExpiredJwtException e) {
            //如果过期了
            return securityContext;
        }
        Claims claims = claimsJws.getBody();
        JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken(((String) claims.get("userId")));
        securityContext.setAuthentication(authenticationToken);

        return securityContext;

    }

    @Override
    public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {
        UserDetails userDetails = (UserDetails) context.getAuthentication().getPrincipal();
        String accessToken = Jwts
                .builder()
                .signWith(secretKey)
                .claim("userId", userDetails.getUsername())
                .setExpiration(new Date(
                        System.currentTimeMillis() + Duration.ofMinutes(2).toMillis()
                ))
                .compact();
        request.setAttribute("accessToken",accessToken);
    }

    @Override
    public boolean containsContext(HttpServletRequest request) {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isBlank(authorization)) {
            return false;
        }
        try {
            PARSER.parseClaimsJws(authorization);
        } catch (Exception e) {
            return false;
        }

        return true;

    }
}
