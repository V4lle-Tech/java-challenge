package com.example.demo.dto;

import java.time.Instant;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateOrderRequest(
                @NotNull UUID id,
                @NotBlank(message = "Description is required") String description,
                @NotNull @JsonProperty("order_date") Instant orderDate) {

}
