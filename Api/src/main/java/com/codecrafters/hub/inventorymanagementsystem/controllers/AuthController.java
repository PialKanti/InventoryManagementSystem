package com.codecrafters.hub.inventorymanagementsystem.controllers;

import com.codecrafters.hub.inventorymanagementsystem.dtos.request.auth.LoginRequest;
import com.codecrafters.hub.inventorymanagementsystem.dtos.request.auth.RegistrationRequest;
import com.codecrafters.hub.inventorymanagementsystem.dtos.response.auth.LoginResponse;
import com.codecrafters.hub.inventorymanagementsystem.dtos.response.users.UserResponse;
import com.codecrafters.hub.inventorymanagementsystem.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/auth")
public class AuthController {
    private final AuthService service;

    @PostMapping(value = "/register")
    public ResponseEntity<UserResponse> register(@RequestBody RegistrationRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(service.login(request));
    }

    @GetMapping(value = "/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String authorizationHeader) {
        service.logout(authorizationHeader);
        return ResponseEntity.noContent().build();
    }
}
