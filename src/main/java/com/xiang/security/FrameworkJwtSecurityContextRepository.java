package com.xiang.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

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
        Jws<Claims> claimsJws = PARSER.parseClaimsJws(authorization);
        claimsJws.getBody();

    }

    @Override
    public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    public boolean containsContext(HttpServletRequest request) {
        return false;
    }
}
