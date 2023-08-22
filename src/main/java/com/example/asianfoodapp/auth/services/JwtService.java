package com.example.asianfoodapp.auth.services;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
        Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJwt(token);
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String getSubject(final String token) {
        return Jwts
                .parser()
                .setSigningKey(SECRET)
                .parseClaimsJwt(token)
                .getBody()
                .getSubject();
    }

    public String refreshToken(final String token, int exp) {
        String username = getSubject(token);
        return generateToken(username, exp);
    }
}
