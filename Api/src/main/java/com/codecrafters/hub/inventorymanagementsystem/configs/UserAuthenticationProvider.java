package com.codecrafters.hub.inventorymanagementsystem.configs;

import com.codecrafters.hub.inventorymanagementsystem.entities.Role;
import com.codecrafters.hub.inventorymanagementsystem.entities.User;
import com.codecrafters.hub.inventorymanagementsystem.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserAuthenticationProvider implements AuthenticationProvider {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var username = authentication.getName();
        var userOptional = userRepository.findByUsername(username, User.class);

        if (userOptional.isEmpty()) {
            log.error("{} not found", username);
            throw new UsernameNotFoundException("User not found");
        }

        User user = userOptional.get();

        String credentials = (String) authentication.getCredentials();

        if (!passwordEncoder.matches(credentials, user.getPassword())) {
            log.error("Incorrect password for {}", username);
            throw new BadCredentialsException("Password does not match");
        }

        log.info("{} has successfully logged in", username);

        List<GrantedAuthority> authorities = new ArrayList<>();

        for (Role role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getKey()));
        }

        return new UsernamePasswordAuthenticationToken(user,
                authentication.getCredentials(),
                authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
