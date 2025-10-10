package com.example.demo.service;

import com.example.demo.dto.CreateOrderRequest;
import com.example.demo.dto.UpdateOrderRequest;
import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Order;
import com.example.demo.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("OrderService Unit Tests")
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    private Order testOrder;
    private CreateOrderRequest testOrderCreateRequest;
    private UpdateOrderRequest testOrderUpdateRequest;
    private UUID testId;

    @BeforeEach
    void setUp() {
        testId = UUID.randomUUID();
        testOrder = new Order();
        testOrder.setDescription("Test Create Order");
        testOrder.setOrderDate(Instant.now());

        // Use reflection to set id for testing purposes
        try {
            java.lang.reflect.Field idField = testOrder.getClass().getSuperclass().getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(testOrder, testId);
        } catch (Exception e) {
            // Fallback: just use a new UUID
        }

        testOrderCreateRequest = new CreateOrderRequest(testId, "Test Create Order", Instant.now());
        testOrderUpdateRequest = new UpdateOrderRequest("Test Update Order", Instant.now());
    }

    @Test
    @DisplayName("Should find all orders")
    void shouldFindAllOrders() {
        // Given
        List<Order> orders = Arrays.asList(testOrder, new Order());
        when(orderRepository.findAll()).thenReturn(orders);

        // When
        List<Order> result = orderService.findAll();

        // Then
        assertThat(result).hasSize(2);
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should find order by id")
    void shouldFindOrderById() {
        // Given
        when(orderRepository.findById(testId)).thenReturn(Optional.of(testOrder));

        // When
        Optional<Order> result = orderService.findById(testId);

        // Then
        assertThat(result).isPresent();
        verify(orderRepository, times(1)).findById(testId);
    }

    @Test
    @DisplayName("Should create order successfully")
    void shouldCreateOrder() {
        // Given
        when(orderRepository.save(any(Order.class))).thenReturn(testOrder);

        // When
        Order result = orderService.create(testOrderCreateRequest);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getDescription()).isEqualTo(testOrderCreateRequest.description());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    @DisplayName("Should throw DuplicateResourceException when id exists")
    void shouldThrowExceptionWhenIdExists() {
        // Given
        when(orderRepository.existsById(testId)).thenReturn(true);

        // When/Then
        assertThatThrownBy(() -> orderService.create(testOrderCreateRequest))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessageContaining("id");

        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    @DisplayName("Should update order successfully")
    void shouldUpdateOrder() {
        // Given

        when(orderRepository.findById(testId)).thenReturn(Optional.of(testOrder));
        when(orderRepository.save(any(Order.class))).thenReturn(testOrder);

        // When
        Order result = orderService.update(testId, testOrderUpdateRequest);

        // Then
        assertThat(result).isNotNull();
        verify(orderRepository, times(1)).findById(testId);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when updating non-existing order")
    void shouldThrowExceptionWhenUpdatingNonExistingOrder() {
        // Given
        UUID nonExistingId = UUID.randomUUID();
        when(orderRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        // When/Then
        assertThatThrownBy(() -> orderService.update(nonExistingId, testOrderUpdateRequest))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Order");

        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    @DisplayName("Should delete order successfully")
    void shouldDeleteOrder() {
        // Given
        when(orderRepository.existsById(testId)).thenReturn(true);
        doNothing().when(orderRepository).deleteById(testId);

        // When
        orderService.delete(testId);

        // Then
        verify(orderRepository, times(1)).existsById(testId);
        verify(orderRepository, times(1)).deleteById(testId);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when deleting non-existing order")
    void shouldThrowExceptionWhenDeletingNonExistingOrder() {
        // Given
        UUID nonExistingId = UUID.randomUUID();
        when(orderRepository.existsById(nonExistingId)).thenReturn(false);

        // When/Then
        assertThatThrownBy(() -> orderService.delete(nonExistingId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Order");

        verify(orderRepository, never()).deleteById(any(UUID.class));
    }
}
