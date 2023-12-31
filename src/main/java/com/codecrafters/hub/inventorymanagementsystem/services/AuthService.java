package com.codecrafters.hub.inventorymanagementsystem.services;

import com.codecrafters.hub.inventorymanagementsystem.entities.BlackListedToken;
import com.codecrafters.hub.inventorymanagementsystem.entities.User;
import com.codecrafters.hub.inventorymanagementsystem.entities.request.auth.LoginRequest;
import com.codecrafters.hub.inventorymanagementsystem.entities.request.auth.RegistrationRequest;
import com.codecrafters.hub.inventorymanagementsystem.entities.response.auth.LoginResponse;
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

    public User register(RegistrationRequest request) {
        return userService.create(request);
    }

    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        User user = userService.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String accessToken = jwtService.generateToken(user);
        return new LoginResponse(accessToken);
    }

    public void logout(String authorizationHeader) {
        String jwtToken = JwtUtils.extractTokenFromAuthorizationHeader(authorizationHeader);
        tokenRepository.save(new BlackListedToken(jwtToken, LocalDateTime.now()));
    }
}
