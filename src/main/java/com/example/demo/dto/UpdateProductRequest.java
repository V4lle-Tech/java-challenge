package com.example.demo.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * DTO for updating an existing Product
 */
public record UpdateProductRequest(
        @NotNull(message = "ID is required") UUID id,

        @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters") String name,

        @Size(max = 500, message = "Description cannot exceed 500 characters") String description,

        @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0") @Digits(integer = 10, fraction = 2, message = "Invalid price format") BigDecimal price,

        @Min(value = 0, message = "Stock cannot be negative") Integer stock,

        String imageUrl,

        UUID categoryId,

        Boolean active) {
}