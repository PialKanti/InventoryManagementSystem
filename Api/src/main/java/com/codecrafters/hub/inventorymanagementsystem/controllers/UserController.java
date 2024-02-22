package com.codecrafters.hub.inventorymanagementsystem.controllers;

import com.codecrafters.hub.inventorymanagementsystem.dtos.request.auth.ChangePasswordRequest;
import com.codecrafters.hub.inventorymanagementsystem.dtos.request.users.UserUpdateRequest;
import com.codecrafters.hub.inventorymanagementsystem.dtos.response.BasePaginatedResponse;
import com.codecrafters.hub.inventorymanagementsystem.dtos.response.users.UserResponse;
import com.codecrafters.hub.inventorymanagementsystem.entities.projections.UserProjection;
import com.codecrafters.hub.inventorymanagementsystem.exceptions.PasswordMismatchException;
import com.codecrafters.hub.inventorymanagementsystem.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<BasePaginatedResponse<UserProjection>> findAll(@RequestParam(name = "page", defaultValue = "0", required = false) int page,
                                                                         @RequestParam(name = "pageSize", defaultValue = "5", required = false) int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return ResponseEntity.ok(userService.findAll(pageable, UserProjection.class));
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<UserProjection> findByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.findByUsername(username, UserProjection.class));
    }

    @PutMapping(value = "/{username}")
    public ResponseEntity<UserResponse> update(@PathVariable String username, @RequestBody UserUpdateRequest request) {
        return ResponseEntity.ok(userService.update(username, request));
    }

    @DeleteMapping(value = "/{username}")
    public ResponseEntity<Void> deleteById(@PathVariable String username) {
        userService.deleteByUsername(username);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{username}/password")
    public ResponseEntity<Map<String, String>> updatePassword(@PathVariable String username, @RequestBody ChangePasswordRequest request) throws PasswordMismatchException {
        userService.updatePassword(username, request);
        return ResponseEntity.noContent().build();
    }
}
