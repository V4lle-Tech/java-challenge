package com.example.demo.util;

import com.example.demo.dto.OrderDTO;
import com.example.demo.model.Order;

/**
 * Mapper for Order entity and DTO
 */
public class OrderMapper {

    public static OrderDTO toDto(Order order) {

        return new OrderDTO(
                order.getId(),
                order.getDescription().toString(),
                order.getOrderDate());
    }

    public static Order toEntity(OrderDTO dto) {
        return new Order(dto.description(), dto.orderDate());
    }
}