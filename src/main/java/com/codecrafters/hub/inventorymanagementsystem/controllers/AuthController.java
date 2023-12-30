package com.codecrafters.hub.inventorymanagementsystem.controllers;

import com.codecrafters.hub.inventorymanagementsystem.entities.User;
import com.codecrafters.hub.inventorymanagementsystem.entities.request.LoginRequest;
import com.codecrafters.hub.inventorymanagementsystem.entities.request.RegistrationRequest;
import com.codecrafters.hub.inventorymanagementsystem.entities.response.LoginResponse;
import com.codecrafters.hub.inventorymanagementsystem.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/auth")
public class AuthController {
    private final AuthService service;

    public AuthController(@Autowired AuthService service) {
        this.service = service;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<User> register(@RequestBody RegistrationRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(service.login(request));
    }
}
