package com.example.demo.util;

import com.example.demo.dto.OrderResponse;
import com.example.demo.model.Order;

/**
 * Mapper for Order entity and DTO
 */
public class OrderResponseMapper {

    public static OrderResponse toDto(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getDescription(),
                order.getOrderDate());
    }
}