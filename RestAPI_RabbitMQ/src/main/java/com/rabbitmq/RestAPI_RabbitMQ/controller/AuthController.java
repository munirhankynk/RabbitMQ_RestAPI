package com.rabbitmq.RestAPI_RabbitMQ.controller;

import com.rabbitmq.RestAPI_RabbitMQ.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody Map<String, String> credentials, HttpServletResponse response) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        try {
            // Kullanıcıyı doğrula ve token oluştur
            String token = authService.authenticateUser(username, password);

            // JWT'yi cookie'ye ekle
            Cookie jwtCookie = new Cookie("JWT", token);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setSecure(true); // HTTPS kullanıyorsanız true olmalı
            jwtCookie.setPath("/");
            jwtCookie.setMaxAge(7 * 24 * 60 * 60); // 7 gün

            response.addCookie(jwtCookie);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }



    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Map<String, String> userDetails) {
        String username = userDetails.get("username");
        String email = userDetails.get("email");
        String password = userDetails.get("password");
        authService.registerUser(username, email, password);
        return ResponseEntity.ok("User registered successfully");
    }
}
