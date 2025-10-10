package com.example.demo.service;

import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Order;
import com.example.demo.dto.CreateOrderRequest;
import com.example.demo.dto.UpdateOrderRequest;
import com.example.demo.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;

    @Transactional
    public Order create(CreateOrderRequest orderRequest) {
        log.info("Creating order with description: {}", orderRequest.description());
        if (orderRequest.id() != null &&
                orderRepository.existsById(orderRequest.id())) {
            throw new DuplicateResourceException("Order", "id", orderRequest.id());
        }

        Order newOrder = new Order(
                orderRequest.description(),
                orderRequest.orderDate());

        Order savedOrder = orderRepository.save(newOrder);
        log.info("Order created with id: {}", savedOrder.getId());
        return savedOrder;
    }

    @Transactional(readOnly = true)
    public Optional<Order> findById(UUID id) {
        log.debug("Finding order by id: {}", id);
        return orderRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Order> findAll() {
        log.debug("Finding all orders");
        return orderRepository.findAll();
    }

    @Transactional
    public Order update(UUID id, UpdateOrderRequest orderRequest) {
        log.info("Updating order with id: {}", id);
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));

        order.setDescription(orderRequest.description());
        order.setOrderDate(orderRequest.orderDate());

        order = orderRepository.save(order);
        log.info("Order updated with id: {}", order.getId());
        return order;
    }

    @Transactional
    public void delete(UUID id) {
        log.info("Deleting order with id: {}", id);
        if (!orderRepository.existsById(id)) {
            throw new ResourceNotFoundException("Order", "id", id);
        }
        orderRepository.deleteById(id);
        log.info("Order deleted with id: {}", id);
    }
}
