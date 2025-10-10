package com.example.demo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

/**
 * DTO for updating an existing Category
 */
public record UpdateCategoryRequest(
        @NotNull(message = "ID is required") UUID id,

        @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters") String name,

        @Size(max = 200, message = "Description cannot exceed 200 characters") String description) {
}