package com.codecrafters.hub.inventorymanagementsystem.dtos.request.auth;

import com.codecrafters.hub.inventorymanagementsystem.entities.Role;
import com.codecrafters.hub.inventorymanagementsystem.enums.UserRole;

import java.util.List;

public class RegistrationRequest {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private List<UserRole> roles;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }
}
