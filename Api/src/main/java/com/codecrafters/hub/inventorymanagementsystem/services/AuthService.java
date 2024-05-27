package com.codecrafters.hub.inventorymanagementsystem.services;

import com.codecrafters.hub.inventorymanagementsystem.dtos.request.auth.LoginRequest;
import com.codecrafters.hub.inventorymanagementsystem.dtos.request.auth.RegistrationRequest;
import com.codecrafters.hub.inventorymanagementsystem.dtos.response.auth.LoginResponse;
import com.codecrafters.hub.inventorymanagementsystem.dtos.response.users.UserResponse;
import com.codecrafters.hub.inventorymanagementsystem.entities.BlackListedToken;
import com.codecrafters.hub.inventorymanagementsystem.entities.User;
import com.codecrafters.hub.inventorymanagementsystem.repositories.BlackListedTokenRepository;
import com.codecrafters.hub.inventorymanagementsystem.utils.JwtUtils;
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
        log.info("Login requested for {}", request.getUsername());
        var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        //User user = userService.findByUsername(request.getUsername(), User.class);
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
