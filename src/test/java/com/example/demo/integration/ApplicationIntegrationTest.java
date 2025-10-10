package com.example.demo.integration;

import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
@DisplayName("Full Application Integration Tests")
class ApplicationIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("Should load application context")
    void shouldLoadApplicationContext() {
        assertThat(userRepository).isNotNull();
        assertThat(productRepository).isNotNull();
        assertThat(categoryRepository).isNotNull();
    }

    @Test
    @DisplayName("Should perform full CRUD cycle on User")
    void shouldPerformFullCRUDOnUser() {
        // Create
        User user = new User();
        user.setName("Integration Test User");
        user.setEmail("integration@test.com");
        User saved = userRepository.save(user);
        assertThat(saved.getId()).isNotNull();

        // Read
        User found = userRepository.findById(saved.getId()).orElseThrow();
        assertThat(found.getName()).isEqualTo("Integration Test User");

        // Update
        found.setName("Updated User");
        User updated = userRepository.save(found);
        assertThat(updated.getName()).isEqualTo("Updated User");

        // Delete
        userRepository.deleteById(updated.getId());
        assertThat(userRepository.findById(updated.getId())).isEmpty();
    }

    @Test
    @DisplayName("Should perform full CRUD cycle on Category")
    void shouldPerformFullCRUDOnCategory() {
        // Create
        Category category = new Category();
        category.setName("Integration Test Category");
        category.setDescription("Test Category Description");

        Category saved = categoryRepository.save(category);
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getCreatedAt()).isNotNull();

        // Read
        Category found = categoryRepository.findById(saved.getId()).orElseThrow();
        assertThat(found.getName()).isEqualTo("Integration Test Category");
        assertThat(found.getDescription()).isEqualTo("Test Category Description");

        // Update
        found.setName("Updated Category");
        found.setDescription("Updated description");
        Category updated = categoryRepository.save(found);
        assertThat(updated.getName()).isEqualTo("Updated Category");
        assertThat(updated.getDescription()).isEqualTo("Updated description");

        // Delete
        categoryRepository.deleteById(updated.getId());
        assertThat(categoryRepository.findById(updated.getId())).isEmpty();
    }

    @Test
    @DisplayName("Should perform full CRUD cycle on Product")
    void shouldPerformFullCRUDOnProduct() {
        // Create
        Product product = new Product();
        product.setName("Integration Test Product");
        product.setDescription("Test Description");
        product.setPrice(new BigDecimal("199.99"));
        product.setStock(15);
        product.setActive(true);

        Product saved = productRepository.save(product);
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getCreatedAt()).isNotNull();

        // Read
        Product found = productRepository.findById(saved.getId()).orElseThrow();
        assertThat(found.getName()).isEqualTo("Integration Test Product");
        assertThat(found.getPrice()).isEqualByComparingTo(new BigDecimal("199.99"));

        // Update
        found.setPrice(new BigDecimal("249.99"));
        found.setStock(20);
        Product updated = productRepository.save(found);
        assertThat(updated.getPrice()).isEqualByComparingTo(new BigDecimal("249.99"));
        assertThat(updated.getStock()).isEqualTo(20);

        // Delete
        productRepository.deleteById(updated.getId());
        assertThat(productRepository.findById(updated.getId())).isEmpty();
    }

    @Test
    @DisplayName("Should handle Category-Product relationship correctly")
    void shouldHandleCategoryProductRelationship() {
        // Create category
        Category category = new Category();
        category.setName("Electronics");
        category.setDescription("Electronic devices");
        Category savedCategory = categoryRepository.save(category);

        // Create product with category
        Product product = new Product();
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(new BigDecimal("99.99"));
        product.setStock(10);
        product.setActive(true);
        product.setCategory(savedCategory);

        Product savedProduct = productRepository.save(product);

        // Verify relationship
        assertThat(savedProduct.getCategory()).isNotNull();
        assertThat(savedProduct.getCategory().getId()).isEqualTo(savedCategory.getId());
        assertThat(savedProduct.getCategory().getName()).isEqualTo("Electronics");

        // Verify bidirectional access
        Product foundProduct = productRepository.findById(savedProduct.getId()).orElseThrow();
        assertThat(foundProduct.getCategory()).isNotNull();
        assertThat(foundProduct.getCategory().getName()).isEqualTo("Electronics");

        // Clean up
        productRepository.deleteById(savedProduct.getId());
        categoryRepository.deleteById(savedCategory.getId());
    }

    @Test
    @DisplayName("Should handle repository queries correctly")
    void shouldHandleRepositoryQueriesCorrectly() {
        // Given
        User user1 = new User();
        user1.setName("User One");
        user1.setEmail("user1@test.com");
        userRepository.save(user1);

        User user2 = new User();
        user2.setName("User Two");
        user2.setEmail("user2@test.com");
        userRepository.save(user2);

        Category category = new Category();
        category.setName("Test Category");
        category.setDescription("Test Description");
        categoryRepository.save(category);

        Product product1 = new Product();
        product1.setName("Active Product 1");
        product1.setPrice(new BigDecimal("99.99"));
        product1.setStock(10);
        product1.setActive(true);
        product1.setCategory(category);
        productRepository.save(product1);

        Product product2 = new Product();
        product2.setName("Inactive Product");
        product2.setPrice(new BigDecimal("49.99"));
        product2.setStock(5);
        product2.setActive(false);
        productRepository.save(product2);

        // When/Then
        assertThat(userRepository.findAll()).hasSizeGreaterThanOrEqualTo(2);
        assertThat(userRepository.existsByEmail("user1@test.com")).isTrue();
        assertThat(productRepository.findByActiveTrue()).hasSizeGreaterThanOrEqualTo(1);
        assertThat(categoryRepository.findAll()).hasSizeGreaterThanOrEqualTo(1);
        assertThat(categoryRepository.existsByName("Test Category")).isTrue();
    }
}
