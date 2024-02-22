package com.codecrafters.hub.inventorymanagementsystem.filters;

import com.codecrafters.hub.inventorymanagementsystem.dtos.response.ErrorResponse;
import com.codecrafters.hub.inventorymanagementsystem.services.JwtService;
import com.codecrafters.hub.inventorymanagementsystem.services.UserService;
import com.codecrafters.hub.inventorymanagementsystem.utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserService userService;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String authHeader = request.getHeader("Authorization");
            final String authHeaderPrefix = "Bearer ";

            if (authHeader == null || !authHeader.startsWith(authHeaderPrefix)) {
                filterChain.doFilter(request, response);
                return;
            }

            String jwtToken = JwtUtils.extractTokenFromAuthorizationHeader(authHeader);
            String username = jwtService.extractUsername(jwtToken);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userService.loadUserByUsername(username);
                if (userDetails == null) {
                    filterChain.doFilter(request, response);
                    return;
                }

                if (jwtService.isTokenValid(jwtToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }

            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException exception) {
            writeErrorMessageToResponse(response, "JWT token expired");
        } catch (UsernameNotFoundException exception) {
            writeErrorMessageToResponse(response, "User not found");
        }
    }

    private void writeErrorMessageToResponse(HttpServletResponse response, String errorMessage) throws IOException {
        HttpStatus status = HttpStatus.UNAUTHORIZED;

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(status.value());
        response.getWriter().write(objectMapper.writeValueAsString(ErrorResponse.build(status, errorMessage)));
    }
}
