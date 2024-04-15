package com.springboot.jwt_authentication.controller;

import com.springboot.jwt_authentication.dto.authentication.LoginRequest;
import com.springboot.jwt_authentication.dto.authentication.LoginResponse;
import com.springboot.jwt_authentication.dto.authentication.RegisterRequest;
import com.springboot.jwt_authentication.dto.authentication.RegisterResponse;
import com.springboot.jwt_authentication.model.User;
import com.springboot.jwt_authentication.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest dto) {
        String jwt = authService.login(dto);
        LoginResponse resBody = LoginResponse.builder()
                .accessToken(jwt)
                .build();
        return ResponseEntity.ok(resBody);
    }

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest dto) {
        User user = authService.register(dto);
        RegisterResponse resBody = RegisterResponse.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
        return ResponseEntity.ok(resBody);
    }
}
