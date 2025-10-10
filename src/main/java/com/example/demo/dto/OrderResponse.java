package com.example.demo.dto;

import java.util.UUID;
import java.time.Instant;

/**
 * DTO for User responses
 */
public record OrderResponse(
        UUID id,
        String description, Instant orderDate) {
}