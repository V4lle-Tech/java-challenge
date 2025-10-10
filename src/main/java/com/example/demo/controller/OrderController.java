package com.example.demo.controller;

import com.example.demo.dto.CreateOrderRequest;
import com.example.demo.dto.UpdateOrderRequest;
import com.example.demo.dto.OrderResponse;
import com.example.demo.util.OrderResponseMapper;
import com.example.demo.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAll() {
        log.debug("Getting all orders");
        List<OrderResponse> orders = orderService.findAll().stream()
                .map(OrderResponseMapper::toDto)
                .toList();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getById(@PathVariable UUID id) {
        log.debug("Getting order by id: {}", id);
        return orderService.findById(id)
                .map(OrderResponseMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<OrderResponse> create(@Valid @RequestBody CreateOrderRequest orderRequest) {
        log.info("Creating order with description: {}", orderRequest.description());
        var order = orderService.create(orderRequest);
        var orderResponse = OrderResponseMapper.toDto(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateOrderRequest orderRequest) {
        log.info("Updating order with id: {}", id);
        var order = orderService.update(id, orderRequest);
        var orderResponse = OrderResponseMapper.toDto(order);
        return ResponseEntity.ok(orderResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        log.info("Deleting order with id: {}", id);
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
