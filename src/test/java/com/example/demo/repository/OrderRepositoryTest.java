package com.example.demo.repository;

import com.example.demo.model.Order;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("OrderRepository Integration Tests")
class OrderRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    @DisplayName("Should save and retrieve order")
    void shouldSaveAndRetrieveOrder() {
        // Given
        Order order = new Order();
        order.setDescription("Test Order");
        order.setOrderDate(Instant.now());

        // When
        Order saved = orderRepository.save(order);

        // Then
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getCreatedAt()).isNotNull();
        assertThat(saved.getUpdatedAt()).isNotNull();

        Optional<Order> retrieved = orderRepository.findById(saved.getId());
        assertThat(retrieved).isPresent();
        assertThat(retrieved.get().getDescription()).isEqualTo("Test Order");
    }

    @Test
    @DisplayName("Should check if order exists by id")
    void shouldCheckIfOrderExists() {
        // Given
        Order order = new Order();
        order.setDescription("Another Order");
        order.setOrderDate(Instant.now());
        Order saved = entityManager.persist(order);
        entityManager.flush();

        // When
        boolean exists = orderRepository.existsById(saved.getId());

        // Then
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Should find all orders")
    void shouldFindAllOrders() {
        // Given
        Order order1 = new Order();
        order1.setDescription("Order 1");
        order1.setOrderDate(Instant.now());
        entityManager.persist(order1);

        Order order2 = new Order();
        order2.setDescription("Order 2");
        order2.setOrderDate(Instant.now());
        entityManager.persist(order2);
        entityManager.flush();

        // When
        var orders = orderRepository.findAll();

        // Then
        assertThat(orders).hasSize(2);
    }
}
