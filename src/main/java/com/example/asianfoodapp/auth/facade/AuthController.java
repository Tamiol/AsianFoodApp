package com.example.asianfoodapp.auth.facade;

import com.example.asianfoodapp.auth.domain.AuthResponse;
import com.example.asianfoodapp.auth.domain.Code;
import com.example.asianfoodapp.auth.domain.dto.UserLoginDTO;
import com.example.asianfoodapp.auth.domain.dto.UserRegisterDTO;
import com.example.asianfoodapp.auth.services.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> addNewUser(@Valid @RequestBody UserRegisterDTO user) {
        userService.register(user);
        return ResponseEntity.ok(new AuthResponse(Code.SUCCESS));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO user, HttpServletResponse response) {
        return userService.login(user, response);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        return userService.logout(request, response);
    }

    @GetMapping("/validate")
    public ResponseEntity<AuthResponse> validateToken(HttpServletRequest request, HttpServletResponse response) {
        try {
            userService.validateToken(request, response);
            return ResponseEntity.ok(new AuthResponse(Code.PERMIT));
        } catch (IllegalArgumentException | ExpiredJwtException e) {
            return ResponseEntity.status(401).body(new AuthResponse(Code.TOKEN_NOT_VALID));
        }
    }

}
