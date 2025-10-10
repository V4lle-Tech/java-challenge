package com.example.demo.dto;

import java.time.Instant;
import java.util.UUID;

public record OrderDTO(
        UUID id,
        String description,
        Instant orderDate) {
}