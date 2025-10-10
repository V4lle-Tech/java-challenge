package com.example.demo.controller;

import com.example.demo.dto.CreateProductRequest;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Product;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@DisplayName("ProductController WebMvcTest Integration Tests")
class ProductControllerWebTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private CategoryService categoryService;

    private Product testProduct;
    private List<Product> productList;
    private UUID testProductId;
    private UUID product2Id;

    @BeforeEach
    void setUp() {
        testProductId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        product2Id = UUID.fromString("456e7890-e89b-12d3-a456-426614174001");

        testProduct = new Product();
        testProduct.setId(testProductId);
        testProduct.setName("Test Product");
        testProduct.setDescription("Test Description");
        testProduct.setPrice(new BigDecimal("99.99"));
        testProduct.setStock(10);
        testProduct.setActive(true);

        Product product2 = new Product();
        product2.setId(product2Id);
        product2.setName("Another Product");
        product2.setDescription("Another Description");
        product2.setPrice(new BigDecimal("149.99"));
        product2.setStock(5);
        product2.setActive(true);

        productList = Arrays.asList(testProduct, product2);
    }

    @Test
    @DisplayName("GET /products should return product list view")
    void shouldReturnProductListView() throws Exception {
        // Given
        when(productService.findAllEntities()).thenReturn(productList);

        // When/Then
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(view().name("products/list"))
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attribute("products", hasSize(2)))
                .andExpect(model().attribute("titulo", "Product List"));

        verify(productService, times(1)).findAllEntities();
    }

    @Test
    @DisplayName("GET /products/{id} should return product detail view")
    void shouldReturnProductDetailView() throws Exception {
        // Given
        when(productService.findByIdEntity(testProductId)).thenReturn(Optional.of(testProduct));

        // When/Then
        mockMvc.perform(get("/products/{id}", testProductId))
                .andExpect(status().isOk())
                .andExpect(view().name("products/detail"))
                .andExpect(model().attributeExists("product"))
                .andExpect(model().attribute("product", testProduct))
                .andExpect(model().attribute("titulo", "Product Details"));

        verify(productService, times(1)).findByIdEntity(testProductId);
    }

    @Test
    @DisplayName("GET /products/{id} should throw exception when product not found")
    void shouldThrowExceptionWhenProductNotFound() throws Exception {
        // Given
        UUID nonExistingId = UUID.fromString("999e4567-e89b-12d3-a456-426614174999");
        when(productService.findByIdEntity(nonExistingId)).thenReturn(Optional.empty());

        // When/Then
        mockMvc.perform(get("/products/{id}", nonExistingId))
                .andExpect(status().isInternalServerError());

        verify(productService, times(1)).findByIdEntity(nonExistingId);
    }

    @Test
    @DisplayName("GET /products/new should return new product form")
    void shouldReturnNewProductForm() throws Exception {
        // When/Then
        mockMvc.perform(get("/products/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("products/form"))
                .andExpect(model().attributeExists("product"))
                .andExpect(model().attribute("titulo", "New Product"));
    }

    @Test
    @DisplayName("POST /products should create product and redirect")
    void shouldCreateProductAndRedirect() throws Exception {
        // Given
        when(productService.saveEntity(any())).thenReturn(testProduct);

        // When/Then
        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "New Product")
                .param("description", "New Description")
                .param("price", "99.99")
                .param("stock", "10"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products"))
                .andExpect(flash().attributeExists("success"))
                .andExpect(flash().attribute("success", "Product created successfully"));

        verify(productService, times(1)).saveEntity(any());
    }

    @Test
    @DisplayName("POST /products should return form with errors for invalid data")
    void shouldReturnFormWithErrorsForInvalidData() throws Exception {
        // When/Then
        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "AB") // Too short
                .param("description", "")
                .param("price", "-10") // Negative price
                .param("stock", "-5")) // Negative stock
                .andExpect(status().isOk())
                .andExpect(view().name("products/form"))
                .andExpect(model().attributeHasFieldErrors("product", "name", "price", "stock"));

        verify(productService, never()).saveEntity(any());
    }

    @Test
    @DisplayName("GET /products/{id}/edit should return edit form")
    void shouldReturnEditForm() throws Exception {
        // Given
        when(productService.findByIdEntity(testProductId)).thenReturn(Optional.of(testProduct));

        // When/Then
        mockMvc.perform(get("/products/{id}/edit", testProductId))
                .andExpect(status().isOk())
                .andExpect(view().name("products/form"))
                .andExpect(model().attributeExists("product"))
                .andExpect(model().attribute("product", testProduct))
                .andExpect(model().attribute("titulo", "Edit Product"));

        verify(productService, times(1)).findByIdEntity(testProductId);
    }

    @Test
    @DisplayName("POST /products/{id} should update product and redirect")
    void shouldUpdateProductAndRedirect() throws Exception {
        // Given
        when(productService.saveEntity(any())).thenReturn(testProduct);

        // When/Then
        mockMvc.perform(post("/products/{id}", testProductId.toString())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "Updated Product")
                .param("description", "Updated Description")
                .param("price", "199.99")
                .param("stock", "20"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products"))
                .andExpect(flash().attributeExists("success"))
                .andExpect(flash().attribute("success", "Product updated successfully"));

        verify(productService, times(1)).saveEntity(any());
    }

    @Test
    @DisplayName("POST /products/{id}/delete should delete product and redirect")
    void shouldDeleteProductAndRedirect() throws Exception {
        // Given
        doNothing().when(productService).deleteById(testProductId);

        // When/Then
        mockMvc.perform(post("/products/{id}/delete", testProductId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products"))
                .andExpect(flash().attributeExists("success"))
                .andExpect(flash().attribute("success", "Product deleted successfully"));

        verify(productService, times(1)).deleteById(testProductId);
    }

    @Test
    @DisplayName("POST /products/{id}/delete should handle ResourceNotFoundException")
    void shouldHandleResourceNotFoundExceptionOnDelete() throws Exception {
        // Given
        UUID nonExistingId = UUID.fromString("999e4567-e89b-12d3-a456-426614174999");
        doThrow(new ResourceNotFoundException("Product", "id", nonExistingId))
                .when(productService).deleteById(nonExistingId);

        // When/Then
        mockMvc.perform(post("/products/{id}/delete", nonExistingId))
                .andExpect(status().isNotFound());

        verify(productService, times(1)).deleteById(nonExistingId);
    }
}
