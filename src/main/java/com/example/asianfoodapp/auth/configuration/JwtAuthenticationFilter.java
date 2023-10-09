package com.example.asianfoodapp.auth.configuration;

import com.example.asianfoodapp.auth.services.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.util.Arrays;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    @Override
    protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response,FilterChain filterChain) throws ServletException, IOException, java.io.IOException {

        try {
            validateToken(request, response);
        } catch (IllegalArgumentException | ExpiredJwtException | NullPointerException e) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = extractToken(request);
        if(token == null) {
            filterChain.doFilter(request, response);
            return;
        }
        Authentication authentication = getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String token = null;
        for(Cookie value: Arrays.stream(request.getCookies()).toList()) {
            if(value.getName().equals("token")) {
                token = value.getValue();
            }
        }
        return token;
    }

    public void validateToken(HttpServletRequest request, HttpServletResponse response) throws ExpiredJwtException, IllegalArgumentException {
        String token = null;
        String refresh = null;
        for(Cookie value: Arrays.stream(request.getCookies()).toList()) {
            if(value.getName().equals("token")) {
                token = value.getValue();
            } else if (value.getName().equals("refresh")) {
                refresh = value.getValue();
            }
        }
        try {
            jwtService.validateToken(token);
        }catch (IllegalArgumentException | ExpiredJwtException e) {
            jwtService.validateToken(refresh);
        }
    }

    public Authentication getAuthentication(String token) {
        String username = jwtService.getSubject(token);
        return new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());
    }
}
