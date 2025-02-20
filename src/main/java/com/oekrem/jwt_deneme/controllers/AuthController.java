package com.oekrem.jwt_deneme.controllers;

import com.oekrem.jwt_deneme.dtos.requests.LoginRequest;
import com.oekrem.jwt_deneme.dtos.responses.AuthResponse;
import com.oekrem.jwt_deneme.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        UserDetails userDetails = authService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        String tokenValue = authService.generateToken(userDetails);
        AuthResponse authResponse = AuthResponse.builder()
                .token(tokenValue)
                .expiresIn(86400000L)
                .build();
        return ResponseEntity.ok(authResponse);
    }
}
