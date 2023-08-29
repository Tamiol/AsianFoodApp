package com.example.asianfoodapp.auth.services;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import java.security.Key;
import java.util.*;

@Service
public class JwtService {

    private final String SECRET;

    public JwtService(@Value("${jwt.secret}") String secret) {
        this.SECRET = secret;
    }

    public String generateToken(String username, int exp) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username, exp);
    }

    private String createToken(Map<String, Object> claims, String username, int exp) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + exp))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    public void validateToken(final String token) throws ExpiredJwtException, IllegalArgumentException {
        Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getSubject(final String token) { // Pobierz nazwę użytkownika z tokenu
        return Jwts
                .parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String refreshToken(final String token, int exp) {
        String username = getSubject(token);
        return generateToken(username, exp);
    }
}
