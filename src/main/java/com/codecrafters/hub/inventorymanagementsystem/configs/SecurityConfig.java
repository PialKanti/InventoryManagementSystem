package com.codecrafters.hub.inventorymanagementsystem.configs;

import com.codecrafters.hub.inventorymanagementsystem.enums.UserPermission;
import com.codecrafters.hub.inventorymanagementsystem.enums.UserRole;
import com.codecrafters.hub.inventorymanagementsystem.filters.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private static final String[] WHITELIST_URLS = {
            "/api/v1/auth/**",
            "/error",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/v3/api-docs/**",
            "swagger-ui.html"
    };
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    public SecurityConfig(@Autowired JwtAuthenticationFilter jwtAuthenticationFilter, @Autowired AuthenticationProvider authenticationProvider) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request.requestMatchers(WHITELIST_URLS)
                        .permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/products").hasRole(UserRole.USER.toString())
                        .requestMatchers(HttpMethod.POST, "/api/v1/products").hasAuthority(UserPermission.ADMIN_CREATE.toString())
                        .requestMatchers(HttpMethod.PUT, "/api/v1/products").hasAuthority(UserPermission.ADMIN_UPDATE.toString())
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/products").hasAuthority(UserPermission.ADMIN_DELETE.toString())
                        .anyRequest()
                        .authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
}
