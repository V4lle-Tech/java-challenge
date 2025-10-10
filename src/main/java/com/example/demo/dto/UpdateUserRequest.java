package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

/**
 * DTO for updating an existing User
 */
public record UpdateUserRequest(
        @NotNull(message = "ID is required") UUID id,

        @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters") String name,

        @Email(message = "Email must be valid") String email) {
}