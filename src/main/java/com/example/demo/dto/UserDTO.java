package com.example.demo.dto;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for User responses
 */
public record UserDTO(
        UUID id,
        String name,
        String email,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
}