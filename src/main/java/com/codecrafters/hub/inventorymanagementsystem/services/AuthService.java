package com.codecrafters.hub.inventorymanagementsystem.services;

import com.codecrafters.hub.inventorymanagementsystem.entities.BlackListedToken;
import com.codecrafters.hub.inventorymanagementsystem.entities.Role;
import com.codecrafters.hub.inventorymanagementsystem.entities.User;
import com.codecrafters.hub.inventorymanagementsystem.entities.request.auth.LoginRequest;
import com.codecrafters.hub.inventorymanagementsystem.entities.request.auth.RegistrationRequest;
import com.codecrafters.hub.inventorymanagementsystem.entities.response.auth.LoginResponse;
import com.codecrafters.hub.inventorymanagementsystem.enums.UserRole;
import com.codecrafters.hub.inventorymanagementsystem.repositories.BlackListedTokenRepository;
import com.codecrafters.hub.inventorymanagementsystem.repositories.RoleRepository;
import com.codecrafters.hub.inventorymanagementsystem.repositories.UserRepository;
import com.codecrafters.hub.inventorymanagementsystem.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BlackListedTokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UserRepository userRepository, RoleRepository roleRepository, BlackListedTokenRepository tokenRepository, AuthenticationManager authenticationManager, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.tokenRepository = tokenRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(RegistrationRequest request) {
        User userToBeCreated = new User(request.getFirstName(), request.getLastName(), request.getUsername(), request.getEmail(), passwordEncoder.encode(request.getPassword()));
        userToBeCreated.setRoles(extractRoleEntities(request.getRoles()));

        return userRepository.save(userToBeCreated);
    }

    private List<Role> extractRoleEntities(List<UserRole> enumRoles) {
        List<Role> roles = new ArrayList<>();
        for (UserRole enumRole : enumRoles) {
            Optional<Role> role = roleRepository.findByKey(enumRole.toString());
            role.ifPresent(roles::add);
        }

        if (roles.isEmpty()) {
            insertDefaultRole(roles);
        }

        return roles;
    }

    private void insertDefaultRole(List<Role> roles) {
        Role defaultRole = roleRepository.findByKey(UserRole.USER.toString())
                .orElseThrow(() -> new NoSuchElementException("User role does exist"));
        roles.add(defaultRole);
    }

    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String accessToken = jwtService.generateToken(user);
        return new LoginResponse(accessToken);
    }

    public void logout(String authorizationHeader) {
        String jwtToken = JwtUtils.extractTokenFromAuthorizationHeader(authorizationHeader);
        tokenRepository.save(new BlackListedToken(jwtToken, LocalDateTime.now()));
    }
}
