package com.example.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for Product responses
 */
public record ProductDTO(
        UUID id,
        String name,
        String description,
        BigDecimal price,
        Integer stock,
        String imageUrl,
        UUID categoryId,
        String categoryName,
        Boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
}