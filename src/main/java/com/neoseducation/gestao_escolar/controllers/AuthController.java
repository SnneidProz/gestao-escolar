package com.neoseducation.gestao_escolar.controllers;

import com.neoseducation.gestao_escolar.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        // Simulação de autenticação (substituir por validação real)
        if ("admin".equals(username) && "admin123".equals(password)) {
            String token = jwtUtil.generateToken(username, "ROLE_ADMIN");
            return ResponseEntity.ok(Map.of("token", token));
        } else if ("usuario".equals(username) && "user123".equals(password)) {
            String token = jwtUtil.generateToken(username, "ROLE_USER");
            return ResponseEntity.ok(Map.of("token", token));
        }

        return ResponseEntity.status(401).body("Credenciais inválidas");
    }
}
