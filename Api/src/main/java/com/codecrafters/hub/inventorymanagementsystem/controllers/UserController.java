package com.codecrafters.hub.inventorymanagementsystem.controllers;

import com.codecrafters.hub.inventorymanagementsystem.dtos.request.auth.ChangePasswordRequest;
import com.codecrafters.hub.inventorymanagementsystem.dtos.request.users.UserUpdateRequest;
import com.codecrafters.hub.inventorymanagementsystem.dtos.response.BasePaginatedResponse;
import com.codecrafters.hub.inventorymanagementsystem.dtos.response.users.UserResponse;
import com.codecrafters.hub.inventorymanagementsystem.entities.projections.UserProjection;
import com.codecrafters.hub.inventorymanagementsystem.exceptions.PasswordMismatchException;
import com.codecrafters.hub.inventorymanagementsystem.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Tag(
        name = "User Management API",
        description = "Endpoints for managing user information, including retrieval, updates, and deletion."
)
public class UserController {
    private final UserService userService;

    @GetMapping
    @Operation(
            summary = "Retrieve all users",
            description = "Fetches a paginated list of users.",
            parameters = {
                    @Parameter(name = "page", description = "Page number to retrieve (default is 0)"),
                    @Parameter(name = "pageSize", description = "Number of users per page (default is 5)")
            }
    )
    public ResponseEntity<BasePaginatedResponse<UserProjection>> findAll(@RequestParam(name = "page", defaultValue = "0", required = false) int page,
                                                                         @RequestParam(name = "pageSize", defaultValue = "5", required = false) int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return ResponseEntity.ok(userService.findAll(pageable, UserProjection.class));
    }

    @GetMapping(value = "/{username}")
    @Operation(
            summary = "Retrieve user by username",
            description = "Fetches a user's details based on the provided username.",
            parameters = @Parameter(name = "username", description = "Username of the user to retrieve", required = true)
    )
    public ResponseEntity<UserProjection> findByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.findByUsername(username, UserProjection.class));
    }

    @PutMapping(value = "/{username}")
    @Operation(
            summary = "Update user information",
            description = "Updates the details of a user identified by their username.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Updated user information",
                    required = true
            ),
            parameters = @Parameter(name = "username", description = "Username of the user to update", required = true)
    )
    public ResponseEntity<UserResponse> update(@PathVariable String username, @RequestBody UserUpdateRequest request) {
        return ResponseEntity.ok(userService.update(username, request));
    }

    @DeleteMapping(value = "/{username}")
    @Operation(
            summary = "Delete a user",
            description = "Deletes the user identified by their username.",
            parameters = @Parameter(name = "username", description = "Username of the user to delete", required = true)
    )
    public ResponseEntity<Void> deleteById(@PathVariable String username) {
        userService.deleteByUsername(username);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{username}/password")
    @Operation(
            summary = "Change user password",
            description = "Updates the password for the user identified by their username.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Request containing old and new passwords",
                    required = true
            ),
            parameters = @Parameter(name = "username", description = "Username of the user whose password is being updated", required = true)
    )
    public ResponseEntity<Map<String, String>> updatePassword(@PathVariable String username, @RequestBody ChangePasswordRequest request) throws PasswordMismatchException {
        userService.updatePassword(username, request);
        return ResponseEntity.noContent().build();
    }
}