package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO for creating a new Category
 */
public record CreateCategoryRequest(
        @NotBlank(message = "Name is required") @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters") String name,

        @Size(max = 200, message = "Description cannot exceed 200 characters") String description) {
}