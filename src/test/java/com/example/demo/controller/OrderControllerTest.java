package com.example.demo.controller;

import com.example.demo.dto.CreateOrderRequest;
import com.example.demo.dto.UpdateOrderRequest;
import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Order;
import com.example.demo.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
@DisplayName("OrderController WebMvcTest Integration Tests")
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    private Order testOrder;
    // private OrderDTO testOrderDTO;
    private CreateOrderRequest testCreateOrderRequest;
    private UpdateOrderRequest testUpdateOrderRequest;
    private UUID testId;
    private String testDescription;
    private Instant testInstant;
    private List<Order> orderList;

    @BeforeEach
    void setUp() {

        testDescription = "Test Order";
        testId = UUID.randomUUID();

        testOrder = new Order();
        testOrder.setDescription(testDescription);
        testOrder.setOrderDate(Instant.now());

        // Set ID using reflection
        try {
            java.lang.reflect.Field idField = testOrder.getClass().getSuperclass().getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(testOrder, testId);
        } catch (Exception e) {
            // Ignore
        }
        testInstant = Instant.now();
        testCreateOrderRequest = new CreateOrderRequest(testId, testDescription, testInstant);
        testUpdateOrderRequest = new UpdateOrderRequest(testDescription + " Editado", testInstant);

        Order order2 = new Order();
        order2.setDescription("Order 2");
        order2.setOrderDate(Instant.now());

        orderList = Arrays.asList(testOrder, order2);
    }

    @Test
    @DisplayName("GET /api/orders should return all orders")
    void shouldGetAllOrders() throws Exception {
        // Given
        when(orderService.findAll()).thenReturn(orderList);

        // When/Then
        mockMvc.perform(get("/api/orders")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].description", is(testDescription)));

        verify(orderService, times(1)).findAll();
    }

    @Test
    @DisplayName("GET /api/orders/{id} should return order when found")
    void shouldGetOrderById() throws Exception {
        // Given
        when(orderService.findById(testId)).thenReturn(Optional.of(testOrder));

        // When/Then
        mockMvc.perform(get("/api/orders/{id}", testId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.description", is(testDescription)));

        verify(orderService, times(1)).findById(testId);
    }

    @Test
    @DisplayName("GET /api/orders/{id} should return 404 when order not found")
    void shouldReturn404WhenOrderNotFound() throws Exception {
        // Given
        UUID nonExistentId = UUID.randomUUID();
        when(orderService.findById(nonExistentId)).thenReturn(Optional.empty());

        // When/Then
        mockMvc.perform(get("/api/orders/{id}", nonExistentId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(orderService, times(1)).findById(nonExistentId);
    }

    @Test
    @DisplayName("POST /api/orders should create order successfully")
    void shouldCreateOrder() throws Exception {

        // Given

        when(orderService.create(any(CreateOrderRequest.class))).thenReturn(testOrder);

        // When/Then
        mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testCreateOrderRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.description", is(testDescription)))
                .andExpect(jsonPath("$.id").exists());

        verify(orderService, times(1)).create(any(CreateOrderRequest.class));
    }

    @Test
    @DisplayName("POST /api/orders should return 409 when ID already exists")
    void shouldReturn409WhenIdExists() throws Exception {
        // Given
        CreateOrderRequest newOrderDTO = new CreateOrderRequest(
                testId,
                "Duplicated Order",
                Instant.now());

        when(orderService.create(any(CreateOrderRequest.class)))
                .thenThrow(new DuplicateResourceException("Order", "id", testId.toString()));

        // When/Then
        mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newOrderDTO)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status", is(409)))
                .andExpect(jsonPath("$.message").exists());

        verify(orderService, times(1)).create(any(CreateOrderRequest.class));
    }

    @Test
    @DisplayName("PUT /api/orders/{id} should update order successfully")
    void shouldUpdateOrder() throws Exception {
        // Given
        Order updatedOrder = new Order();
        updatedOrder.setId(testId);
        updatedOrder.setDescription(testUpdateOrderRequest.description());
        updatedOrder.setOrderDate(Instant.now());

        when(orderService.update(eq(testId), any(UpdateOrderRequest.class))).thenReturn(updatedOrder);

        // When/Then
        mockMvc.perform(put("/api/orders/{id}", testId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testUpdateOrderRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.description", is(testUpdateOrderRequest.description())));

        verify(orderService, times(1)).update(eq(testId), any(UpdateOrderRequest.class));
    }

    @Test
    @DisplayName("PUT /api/orders/{id} should return 404 when order not found")
    void shouldReturn404WhenUpdatingNonExistentOrder() throws Exception {
        // Given
        UUID nonExistentId = UUID.randomUUID();

        when(orderService.update(eq(nonExistentId), any(UpdateOrderRequest.class)))
                .thenThrow(new ResourceNotFoundException("Order", "id", nonExistentId));

        // When/Then
        mockMvc.perform(put("/api/orders/{id}", nonExistentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testUpdateOrderRequest)))
                .andExpect(status().isNotFound());

        verify(orderService, times(1)).update(eq(nonExistentId), any(UpdateOrderRequest.class));
    }

    @Test
    @DisplayName("DELETE /api/orders/{id} should delete order successfully")
    void shouldDeleteOrder() throws Exception {
        // Given
        doNothing().when(orderService).delete(testId);

        // When/Then
        mockMvc.perform(delete("/api/orders/{id}", testId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(orderService, times(1)).delete(testId);
    }

    @Test
    @DisplayName("DELETE /api/orders/{id} should return 404 when order not found")
    void shouldReturn404WhenDeletingNonExistentOrder() throws Exception {
        // Given
        UUID nonExistentId = UUID.randomUUID();
        doThrow(new ResourceNotFoundException("Order", "id", nonExistentId))
                .when(orderService).delete(nonExistentId);

        // When/Then
        mockMvc.perform(delete("/api/orders/{id}", nonExistentId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(orderService, times(1)).delete(nonExistentId);
    }
}
