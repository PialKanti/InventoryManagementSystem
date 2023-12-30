package com.codecrafters.hub.inventorymanagementsystem.services;

import com.codecrafters.hub.inventorymanagementsystem.entities.User;
import com.codecrafters.hub.inventorymanagementsystem.entities.request.LoginRequest;
import com.codecrafters.hub.inventorymanagementsystem.entities.request.RegistrationRequest;
import com.codecrafters.hub.inventorymanagementsystem.entities.response.LoginResponse;
import com.codecrafters.hub.inventorymanagementsystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(@Autowired UserRepository userRepository, @Autowired AuthenticationManager authenticationManager, @Autowired JwtService jwtService) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public User register(RegistrationRequest request) {
        User userToBeCreated = new User(request.getFirstName(), request.getLastName(), request.getUsername(), request.getEmail(), request.getPassword());
        userToBeCreated.setRoles(request.getRoles());

        return userRepository.save(userToBeCreated);
    }

    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String accessToken = jwtService.generateToken(user);
        return new LoginResponse(accessToken);
    }
}
