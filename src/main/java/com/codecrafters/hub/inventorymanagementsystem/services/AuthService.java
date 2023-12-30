package com.codecrafters.hub.inventorymanagementsystem.services;

import com.codecrafters.hub.inventorymanagementsystem.entities.Role;
import com.codecrafters.hub.inventorymanagementsystem.entities.User;
import com.codecrafters.hub.inventorymanagementsystem.entities.request.LoginRequest;
import com.codecrafters.hub.inventorymanagementsystem.entities.request.RegistrationRequest;
import com.codecrafters.hub.inventorymanagementsystem.entities.response.LoginResponse;
import com.codecrafters.hub.inventorymanagementsystem.enums.UserRole;
import com.codecrafters.hub.inventorymanagementsystem.repositories.RoleRepository;
import com.codecrafters.hub.inventorymanagementsystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(@Autowired UserRepository userRepository, @Autowired RoleRepository roleRepository, @Autowired AuthenticationManager authenticationManager, @Autowired JwtService jwtService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public User register(RegistrationRequest request) {
        User userToBeCreated = new User(request.getFirstName(), request.getLastName(), request.getUsername(), request.getEmail(), request.getPassword());
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
}
