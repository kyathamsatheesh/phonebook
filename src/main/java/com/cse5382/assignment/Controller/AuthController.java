package com.cse5382.assignment.Controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cse5382.assignment.Component.JwtUtil;
import com.cse5382.assignment.Config.AuthUserProperties;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final AuthUserProperties authUsersProperties;

    public AuthController(JwtUtil jwtUtil, AuthUserProperties authUsersProperties) {
        this.jwtUtil = jwtUtil;
        this.authUsersProperties = authUsersProperties;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        for (AuthUserProperties.User user : authUsersProperties.getUsers()) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                String token = jwtUtil.generateToken(username, user.getRoles());
                return ResponseEntity.ok(Map.of(
                        "token", token,
                        "role", user.getRoles()
                ));
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Invalid credentials"));
    }
}


