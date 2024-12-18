package com.codecrafters.hub.inventorymanagementsystem.service;

import com.codecrafters.hub.inventorymanagementsystem.model.dto.request.auth.ChangePasswordRequest;
import com.codecrafters.hub.inventorymanagementsystem.model.dto.request.auth.RegistrationRequest;
import com.codecrafters.hub.inventorymanagementsystem.model.dto.request.users.UserUpdateRequest;
import com.codecrafters.hub.inventorymanagementsystem.model.dto.response.users.UserResponse;
import com.codecrafters.hub.inventorymanagementsystem.model.entity.Role;
import com.codecrafters.hub.inventorymanagementsystem.model.entity.User;
import com.codecrafters.hub.inventorymanagementsystem.model.enums.ExceptionConstant;
import com.codecrafters.hub.inventorymanagementsystem.model.enums.UserRole;
import com.codecrafters.hub.inventorymanagementsystem.exception.PasswordMismatchException;
import com.codecrafters.hub.inventorymanagementsystem.repository.RoleRepository;
import com.codecrafters.hub.inventorymanagementsystem.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = "users")
public class UserService extends BaseCrudService<User, Long> implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder,
                       ObjectMapper objectMapper) {
        super(userRepository, objectMapper);
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Cacheable(key = "#username")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByUsername(username, UserDetails.class);
    }

    public <T> T findByUsername(String username, Class<T> type) {
        return userRepository.findByUsername(username, type)
                .orElseThrow(() -> new UsernameNotFoundException(ExceptionConstant.USER_NOT_FOUND.getMessage()));
    }

    public UserResponse create(RegistrationRequest request) {
        User user = mapToEntity(request);
        return mapToDto(save(user), UserResponse.class);
    }

    @CacheEvict(key = "#username")
    public UserResponse update(String username, UserUpdateRequest request) throws EntityNotFoundException {
        User user = findByUsername(username, User.class);
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setRoles(extractRoleEntities(request.roles()));

        return mapToDto(save(user), UserResponse.class);
    }

    public void updatePassword(String username, ChangePasswordRequest request) throws PasswordMismatchException {
        User user = findByUsername(username, User.class);

        if (!passwordEncoder.matches(request.oldPassword(), user.getPassword())) {
            throw new PasswordMismatchException(ExceptionConstant.INVALID_OLD_PASSWORD.getMessage());
        }

        user.setPassword(passwordEncoder.encode(request.newPassword()));
        save(user);
    }

    @Transactional
    public void deleteByUsername(String username) {
        if (!userRepository.existsByUsername(username)) {
            throw new EntityNotFoundException();
        }

        userRepository.deleteByUsername(username);
    }

    private List<Role> extractRoleEntities(List<UserRole> enumRoles) {
        List<Role> roles = new ArrayList<>();

        if (enumRoles == null || enumRoles.isEmpty()) {
            roles.add(getDefaultRole());
            return roles;
        }

        for (UserRole enumRole : enumRoles) {
            Optional<Role> role = roleRepository.findByKey(enumRole.toString());
            role.ifPresent(roles::add);
        }

        return roles;
    }

    private Role getDefaultRole() {
        return roleRepository.findByKey(UserRole.USER.toString())
                .orElseThrow(() -> new EntityNotFoundException(ExceptionConstant.USER_ROLE_NOT_FOUND.getMessage()));
    }

    private User mapToEntity(RegistrationRequest request) {
        return User
                .builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .roles(extractRoleEntities(request.roles()))
                .build();
    }

    @Override
    protected String getEntityNotFoundMessage() {
        return ExceptionConstant.USER_NOT_FOUND.getMessage();
    }
}
