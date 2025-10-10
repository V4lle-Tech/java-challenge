package com.example.demo.service;

import com.example.demo.dto.CreateProductRequest;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Product;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProductService Unit Tests")
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductService productService;

    private Product testProduct;
    private CreateProductRequest testProductCreateRequest;
    private UUID testProductId;

    @BeforeEach
    void setUp() {
        testProductId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        testProduct = new Product();
        testProduct.setId(testProductId);
        testProduct.setName("Test Product");
        testProduct.setDescription("Test Description");
        testProduct.setPrice(new BigDecimal("99.99"));
        testProduct.setStock(1);
        testProduct.setImageUrl("http://image");
        testProduct.setActive(true);

        testProductCreateRequest = new CreateProductRequest(
                "Test Product",
                "Test Description",
                new BigDecimal("99.99"),
                1, "http://image", UUID.randomUUID());

    }

    @Test
    @DisplayName("Should find all products")
    void shouldFindAllProducts() {
        // Given
        List<Product> products = Arrays.asList(testProduct, new Product());
        when(productRepository.findAll()).thenReturn(products);

        // When
        List<Product> result = productService.findAll();

        // Then
        assertThat(result).hasSize(2);
        verify(productRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should find product by id")
    void shouldFindProductById() {
        // Given
        when(productRepository.findById(testProductId)).thenReturn(Optional.of(testProduct));

        // When
        Optional<Product> result = productService.findById(testProductId);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(testProduct);
        verify(productRepository, times(1)).findById(testProductId);
    }

    @Test
    @DisplayName("Should get product by id")
    void shouldGetProductById() {
        // Given
        when(productRepository.findById(testProductId)).thenReturn(Optional.of(testProduct));

        // When
        Product result = productService.getById(testProductId);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(testProductId);
        verify(productRepository, times(1)).findById(testProductId);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when product not found")
    void shouldThrowExceptionWhenProductNotFound() {
        // Given
        UUID nonExistingId = UUID.fromString("999e4567-e89b-12d3-a456-426614174999");
        when(productRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        // When/Then
        assertThatThrownBy(() -> productService.getById(nonExistingId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Product");

        verify(productRepository, times(1)).findById(nonExistingId);
    }

    @Test
    @DisplayName("Should save product successfully")
    void shouldSaveProduct() {
        // Given
        when(productRepository.save(any(Product.class))).thenReturn(testProduct);

        // When
        Product result = productService.save(testProductCreateRequest);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(testProduct.getName());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    @DisplayName("Should delete product successfully")
    void shouldDeleteProduct() {
        // Given
        when(productRepository.existsById(testProductId)).thenReturn(true);
        doNothing().when(productRepository).deleteById(testProductId);

        // When
        productService.deleteById(testProductId);

        // Then
        verify(productRepository, times(1)).existsById(testProductId);
        verify(productRepository, times(1)).deleteById(testProductId);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when deleting non-existing product")
    void shouldThrowExceptionWhenDeletingNonExistingProduct() {
        // Given
        UUID nonExistingId = UUID.fromString("999e4567-e89b-12d3-a456-426614174999");
        when(productRepository.existsById(nonExistingId)).thenReturn(false);

        // When/Then
        assertThatThrownBy(() -> productService.deleteById(nonExistingId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Product");

        verify(productRepository, times(1)).existsById(nonExistingId);
        verify(productRepository, never()).deleteById(any(UUID.class));
    }

    @Test
    @DisplayName("Should count products")
    void shouldCountProducts() {
        // Given
        when(productRepository.count()).thenReturn(5L);

        // When
        long result = productService.count();

        // Then
        assertThat(result).isEqualTo(5L);
        verify(productRepository, times(1)).count();
    }

    @Test
    @DisplayName("Should search products by name")
    void shouldSearchProductsByName() {
        // Given
        List<Product> products = Arrays.asList(testProduct);
        when(productRepository.findByNameContainingIgnoreCase("Test")).thenReturn(products);

        // When
        List<Product> result = productService.searchByName("Test");

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).contains("Test");
        verify(productRepository, times(1)).findByNameContainingIgnoreCase("Test");
    }

    @Test
    @DisplayName("Should find active products")
    void shouldFindActiveProducts() {
        // Given
        List<Product> activeProducts = Arrays.asList(testProduct);
        when(productRepository.findByActiveTrue()).thenReturn(activeProducts);

        // When
        List<Product> result = productService.findActiveProducts();

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getActive()).isTrue();
        verify(productRepository, times(1)).findByActiveTrue();
    }
}
