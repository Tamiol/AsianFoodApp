package com.example.asianfoodapp.auth.services;

import com.example.asianfoodapp.auth.domain.AuthResponse;
import com.example.asianfoodapp.auth.domain.Code;
import com.example.asianfoodapp.auth.domain.Role;
import com.example.asianfoodapp.auth.domain.User;
import com.example.asianfoodapp.auth.domain.dto.UserLoginDTO;
import com.example.asianfoodapp.auth.domain.dto.UserRegisterDTO;
import com.example.asianfoodapp.auth.exceptions.UserEmailAlreadyExistsException;
import com.example.asianfoodapp.auth.exceptions.UsernameAlreadyExistsException;
import com.example.asianfoodapp.auth.repository.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final CookieService cookieService;
    private final AuthenticationManager authenticationManager;
    @Value("${jwt.exp}")
    private int exp;
    @Value("${jwt.refresh.exp}")
    private int refreshExp;

    @Transactional
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Transactional
    public void  register(UserRegisterDTO userRegisterDTO) {

        userRepository.findUserByLogin(userRegisterDTO.getLogin())
                .ifPresent(value -> {
                    throw new UsernameAlreadyExistsException();
                });

        userRepository.findUserByEmail(userRegisterDTO.getEmail())
                .ifPresent(value -> {
                    throw new UserEmailAlreadyExistsException();
                });

        User user = new User();
        user.setLogin(userRegisterDTO.getLogin());
        user.setPassword(userRegisterDTO.getPassword());
        user.setEmail(userRegisterDTO.getEmail());
        user.setRole(Role.USER);

        saveUser(user);
    }

    public ResponseEntity<?> login(UserLoginDTO authRequest, HttpServletResponse response) {
        User user = userRepository.findUserByLogin(authRequest.getLogin()).orElse(null);
        if (user != null){
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getLogin(), authRequest.getPassword()));
            if(authenticate.isAuthenticated()){
                Cookie cookie = cookieService.generateCookie("token", generateToken(authRequest.getLogin(), exp), exp);
                Cookie refresh = cookieService.generateCookie("refresh", generateToken(authRequest.getLogin(), refreshExp), refreshExp);
                response.addCookie(cookie);
                response.addCookie(refresh);
                return ResponseEntity.ok(
                        UserRegisterDTO
                                .builder()
                                .login(user.getUsername())
                                .email(user.getEmail())
                                .role(user.getRole())
                                .build());
            }else {
                return ResponseEntity.ok(new AuthResponse(Code.WRONG_PASSWORD));
            }
        }
        return ResponseEntity.ok(new AuthResponse(Code.NOT_FOUND));
    }

    private String generateToken(String username, int exp) {
        return jwtService.generateToken(username, exp);
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
            Cookie refreshCookie = cookieService.generateCookie("token", jwtService.refreshToken(refresh, refreshExp), refreshExp);
            Cookie cookie = cookieService.generateCookie("token", jwtService.refreshToken(refresh, exp), exp);
            response.addCookie(cookie);
            response.addCookie(refreshCookie);
        }
    }
}
