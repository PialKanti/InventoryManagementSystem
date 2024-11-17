package com.codecrafters.hub.inventorymanagementsystem.controllers;

import com.codecrafters.hub.inventorymanagementsystem.dtos.request.auth.LoginRequest;
import com.codecrafters.hub.inventorymanagementsystem.dtos.request.auth.RegistrationRequest;
import com.codecrafters.hub.inventorymanagementsystem.dtos.response.auth.LoginResponse;
import com.codecrafters.hub.inventorymanagementsystem.dtos.response.users.UserResponse;
import com.codecrafters.hub.inventorymanagementsystem.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/auth")
@Tag(
        name = "Authentication API",
        description = "Endpoints for user authentication, including registration, login, and logout."
)
public class AuthController {
    private final AuthService service;

    @PostMapping(value = "/register")
    @Operation(
            summary = "Register a new user",
            description = "Registers a new user with the provided registration details.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Details of the user to register",
                    required = true
            )
    )
    public ResponseEntity<UserResponse> register(@RequestBody RegistrationRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping(value = "/login")
    @Operation(
            summary = "Login a user",
            description = "Authenticates a user with the provided login details.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Login credentials",
                    required = true
            )
    )
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(service.login(request));
    }

    @GetMapping(value = "/logout")
    @Operation(
            summary = "Logout the user",
            description = "Logs out the user using the provided authorization token.",
            parameters = @Parameter(
                    name = "Authorization",
                    description = "Bearer token for authorization",
                    required = true,
                    in = ParameterIn.HEADER
            )
    )
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String authorizationHeader) {
        service.logout(authorizationHeader);
        return ResponseEntity.noContent().build();
    }
}