package com.example.demo.repository;

import com.example.demo.model.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("ProductRepository Integration Tests")
class ProductRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("Should save and retrieve product")
    void shouldSaveAndRetrieveProduct() {
        // Given
        Product product = new Product();
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(new BigDecimal("99.99"));
        product.setStock(10);
        product.setActive(true);

        // When
        Product saved = productRepository.save(product);

        // Then
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getCreatedAt()).isNotNull();
        assertThat(saved.getUpdatedAt()).isNotNull();

        Optional<Product> retrieved = productRepository.findById(saved.getId());
        assertThat(retrieved).isPresent();
        assertThat(retrieved.get().getName()).isEqualTo("Test Product");
    }

    @Test
    @DisplayName("Should find products by active status")
    void shouldFindByActiveTrue() {
        // Given
        Product activeProduct = new Product();
        activeProduct.setName("Active Product");
        activeProduct.setPrice(new BigDecimal("50.00"));
        activeProduct.setStock(5);
        activeProduct.setActive(true);
        entityManager.persist(activeProduct);

        Product inactiveProduct = new Product();
        inactiveProduct.setName("Inactive Product");
        inactiveProduct.setPrice(new BigDecimal("30.00"));
        inactiveProduct.setStock(3);
        inactiveProduct.setActive(false);
        entityManager.persist(inactiveProduct);
        entityManager.flush();

        // When
        List<Product> activeProducts = productRepository.findByActiveTrue();

        // Then
        assertThat(activeProducts).hasSize(1);
        assertThat(activeProducts.get(0).getName()).isEqualTo("Active Product");
    }

    @Test
    @DisplayName("Should find products by name containing ignore case")
    void shouldFindByNameContainingIgnoreCase() {
        // Given
        Product product1 = new Product();
        product1.setName("Laptop Dell");
        product1.setPrice(new BigDecimal("999.99"));
        product1.setStock(5);
        entityManager.persist(product1);

        Product product2 = new Product();
        product2.setName("Mouse Logitech");
        product2.setPrice(new BigDecimal("49.99"));
        product2.setStock(20);
        entityManager.persist(product2);

        Product product3 = new Product();
        product3.setName("DELL Monitor");
        product3.setPrice(new BigDecimal("299.99"));
        product3.setStock(10);
        entityManager.persist(product3);
        entityManager.flush();

        // When
        List<Product> dellProducts = productRepository.findByNameContainingIgnoreCase("dell");

        // Then
        assertThat(dellProducts).hasSize(2);
        assertThat(dellProducts).extracting(Product::getName)
                .containsExactlyInAnyOrder("Laptop Dell", "DELL Monitor");
    }

    @Test
    @DisplayName("Should find product by id and active true")
    void shouldFindByIdAndActiveTrue() {
        // Given
        Product product = new Product();
        product.setName("Active Product");
        product.setPrice(new BigDecimal("100.00"));
        product.setStock(5);
        product.setActive(true);
        Product saved = entityManager.persist(product);
        entityManager.flush();

        // When
        Optional<Product> found = productRepository.findByIdAndActiveTrue(saved.getId());

        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Active Product");
    }
}
