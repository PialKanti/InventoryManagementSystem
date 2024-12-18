package com.codecrafters.hub.inventorymanagementsystem.service;

import com.codecrafters.hub.inventorymanagementsystem.model.dto.request.auth.LoginRequest;
import com.codecrafters.hub.inventorymanagementsystem.model.dto.request.auth.RegistrationRequest;
import com.codecrafters.hub.inventorymanagementsystem.model.dto.response.auth.LoginResponse;
import com.codecrafters.hub.inventorymanagementsystem.model.dto.response.users.UserResponse;
import com.codecrafters.hub.inventorymanagementsystem.model.entity.BlackListedToken;
import com.codecrafters.hub.inventorymanagementsystem.model.entity.User;
import com.codecrafters.hub.inventorymanagementsystem.repository.BlackListedTokenRepository;
import com.codecrafters.hub.inventorymanagementsystem.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserService userService;
    private final BlackListedTokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserResponse register(RegistrationRequest request) {
        return userService.create(request);
    }

    public LoginResponse login(LoginRequest request) {
        log.info("Login requested for {}", request.username());
        var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));

        var user = (User) authentication.getPrincipal();
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
                .build());
    }
}
