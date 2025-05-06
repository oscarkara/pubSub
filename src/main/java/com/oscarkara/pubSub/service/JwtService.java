package com.oscarkara.pubSub.service;

import com.oscarkara.pubSub.security.UserLoginDetails;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    private static final long EXPIRATION = 1000 * 60 * 100; // 1 hora

    public String generateToken(UserLoginDetails user) {
        return Jwts.builder()
                .setSubject(user.user().getId().toString())
                .claim("role", user.user().getRole().name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }
    public UUID extractUserId(String token) {
        String subject = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

        return UUID.fromString(subject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        UUID userId = extractUserId(token);
        if (userDetails instanceof UserLoginDetails details) {
            return userId.equals(details.user().getId()) && !isTokenExpired(token);
        }
        return false;
    }

    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }
}
