package com.codecrafters.hub.inventorymanagementsystem.model.dto.request.auth;

import com.codecrafters.hub.inventorymanagementsystem.model.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record RegistrationRequest(@NotBlank(message = "First name can not be blank")
                                  @Size(min = 2, max = 35, message = "First name should be between 2 and 35 characters long.")
                                  String firstName,
                                  @NotBlank(message = "Last name can not be blank")
                                  @Size(min = 2, max = 35, message = "Last name should be between 2 and 35 characters long.")
                                  String lastName,
                                  @NotBlank(message = "Username can not be blank")
                                  @Size(min = 3, max = 35, message = "Username should be between 3 and 10 characters long")
                                  String username,
                                  @NotBlank(message = "Email can not be blank")
                                  @Email(message = "Please provide a valid email address")
                                  String email,
                                  @NotBlank(message = "Password can not be blank")
                                  String password,
                                  List<UserRole> roles) {
}