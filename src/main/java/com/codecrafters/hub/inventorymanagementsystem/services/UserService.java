package com.codecrafters.hub.inventorymanagementsystem.services;

import com.codecrafters.hub.inventorymanagementsystem.entities.Role;
import com.codecrafters.hub.inventorymanagementsystem.entities.User;
import com.codecrafters.hub.inventorymanagementsystem.entities.request.auth.ChangePasswordRequest;
import com.codecrafters.hub.inventorymanagementsystem.entities.request.auth.RegistrationRequest;
import com.codecrafters.hub.inventorymanagementsystem.entities.request.users.UserUpdateRequest;
import com.codecrafters.hub.inventorymanagementsystem.enums.UserRole;
import com.codecrafters.hub.inventorymanagementsystem.exceptions.PasswordMismatchException;
import com.codecrafters.hub.inventorymanagementsystem.repositories.RoleRepository;
import com.codecrafters.hub.inventorymanagementsystem.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service

public class UserService extends BaseService<User, Long, RegistrationRequest, UserUpdateRequest> implements UserDetailsService {
    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository repository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        super(repository);
        this.repository = repository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public Optional<User> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    protected User convertToCreateEntity(RegistrationRequest request) {
        User user = new User(request.getFirstName(), request.getLastName(), request.getUsername(), request.getEmail(), passwordEncoder.encode(request.getPassword()));
        user.setRoles(extractRoleEntities(request.getRoles()));
        return user;
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

    @Override
    protected User convertToUpdateEntity(UserUpdateRequest request) {
        User user = repository.findById(request.getId()).orElseThrow(EntityNotFoundException::new);
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        return null;
    }

    public void updatePassword(Long userId, ChangePasswordRequest request) throws PasswordMismatchException {
        User user = repository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new PasswordMismatchException("Invalid old password");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        repository.save(user);
    }
}
