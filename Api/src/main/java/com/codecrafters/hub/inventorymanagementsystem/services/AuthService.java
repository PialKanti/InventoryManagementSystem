package com.codecrafters.hub.inventorymanagementsystem.services;

import com.codecrafters.hub.inventorymanagementsystem.dtos.response.users.UserResponse;
import com.codecrafters.hub.inventorymanagementsystem.entities.BlackListedToken;
import com.codecrafters.hub.inventorymanagementsystem.entities.User;
import com.codecrafters.hub.inventorymanagementsystem.dtos.request.auth.LoginRequest;
import com.codecrafters.hub.inventorymanagementsystem.dtos.request.auth.RegistrationRequest;
import com.codecrafters.hub.inventorymanagementsystem.dtos.response.auth.LoginResponse;
import com.codecrafters.hub.inventorymanagementsystem.repositories.BlackListedTokenRepository;
import com.codecrafters.hub.inventorymanagementsystem.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {
    private final UserService userService;
    private final BlackListedTokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Autowired
    public AuthService(UserService userService, BlackListedTokenRepository tokenRepository, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userService = userService;
        this.tokenRepository = tokenRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public UserResponse register(RegistrationRequest request) {
        return userService.create(request);
    }

    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        User user = userService.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String accessToken = jwtService.generateToken(user);
        return LoginResponse
                .builder()
                .accessToken(accessToken)
                .build();
    }

    public void logout(String authorizationHeader) {
        String jwtToken = JwtUtils.extractTokenFromAuthorizationHeader(authorizationHeader);
        tokenRepository.save(BlackListedToken
                .builder()
                .token(jwtToken)
                .expiryDateTime(LocalDateTime.now())
                .build());
    }
}
