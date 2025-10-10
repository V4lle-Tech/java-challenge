package com.example.demo.dto;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for Category responses
 */
public record CategoryDTO(
        UUID id,
        String name,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
}