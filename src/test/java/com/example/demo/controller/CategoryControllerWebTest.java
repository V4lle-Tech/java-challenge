package com.example.demo.controller;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Category;
import com.example.demo.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * WebMvcTest for CategoryController
 */
@WebMvcTest(CategoryController.class)
@DisplayName("CategoryController WebMvcTest Integration Tests")
class CategoryControllerWebTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    private Category testCategory;
    private List<Category> categoryList;
    private UUID testCategoryId;
    private UUID category2Id;

    @BeforeEach
    void setUp() {
        testCategoryId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        category2Id = UUID.fromString("456e7890-e89b-12d3-a456-426614174001");

        testCategory = new Category();
        testCategory.setId(testCategoryId);
        testCategory.setName("Electronics");
        testCategory.setDescription("Electronic devices and accessories");

        Category category2 = new Category();
        category2.setId(category2Id);
        category2.setName("Books");
        category2.setDescription("Books and publications");

        categoryList = Arrays.asList(testCategory, category2);
    }

    @Test
    @DisplayName("GET /categories should return category list view")
    void shouldReturnCategoryListView() throws Exception {
        // Given
        when(categoryService.findAllEntities()).thenReturn(categoryList);

        // When/Then
        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(view().name("categories/list"))
                .andExpect(model().attributeExists("categories"))
                .andExpect(model().attribute("categories", hasSize(2)))
                .andExpect(model().attribute("titulo", "Category List"));

        verify(categoryService, times(1)).findAllEntities();
    }

    @Test
    @DisplayName("GET /categories/{id} should return category detail view")
    void shouldReturnCategoryDetailView() throws Exception {
        // Given
        when(categoryService.findByIdEntity(testCategoryId)).thenReturn(Optional.of(testCategory));

        // When/Then
        mockMvc.perform(get("/categories/{id}", testCategoryId))
                .andExpect(status().isOk())
                .andExpect(view().name("categories/detail"))
                .andExpect(model().attributeExists("category"))
                .andExpect(model().attribute("category", testCategory))
                .andExpect(model().attribute("titulo", "Category Details"));

        verify(categoryService, times(1)).findByIdEntity(testCategoryId);
    }

    @Test
    @DisplayName("GET /categories/{id} should throw exception when category not found")
    void shouldThrowExceptionWhenCategoryNotFound() throws Exception {
        // Given
        UUID nonExistingId = UUID.fromString("999e4567-e89b-12d3-a456-426614174999");
        when(categoryService.findByIdEntity(nonExistingId)).thenReturn(Optional.empty());

        // When/Then
        mockMvc.perform(get("/categories/{id}", nonExistingId))
                .andExpect(status().isInternalServerError());

        verify(categoryService, times(1)).findByIdEntity(nonExistingId);
    }

    @Test
    @DisplayName("GET /categories/new should return new category form")
    void shouldReturnNewCategoryForm() throws Exception {
        // When/Then
        mockMvc.perform(get("/categories/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("categories/form"))
                .andExpect(model().attributeExists("category"))
                .andExpect(model().attribute("titulo", "New Category"));
    }

    @Test
    @DisplayName("POST /categories should create category and redirect")
    void shouldCreateCategoryAndRedirect() throws Exception {
        // Given
        when(categoryService.saveEntity(any())).thenReturn(testCategory);

        // When/Then
        mockMvc.perform(post("/categories")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "New Category")
                .param("description", "New Description"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/categories"))
                .andExpect(flash().attributeExists("success"))
                .andExpect(flash().attribute("success", "Category created successfully"));

        verify(categoryService, times(1)).saveEntity(any());
    }

    @Test
    @DisplayName("POST /categories should return form with errors for invalid data")
    void shouldReturnFormWithErrorsForInvalidData() throws Exception {
        // When/Then
        mockMvc.perform(post("/categories")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "A") // Too short (min 2 chars)
                .param("description", "")) // Empty description is allowed
                .andExpect(status().isOk())
                .andExpect(view().name("categories/form"))
                .andExpect(model().attributeHasFieldErrors("category", "name"));

        verify(categoryService, never()).save(any());
    }

    @Test
    @DisplayName("GET /categories/{id}/edit should return edit form")
    void shouldReturnEditForm() throws Exception {
        // Given
        when(categoryService.findByIdEntity(testCategoryId)).thenReturn(Optional.of(testCategory));

        // When/Then
        mockMvc.perform(get("/categories/{id}/edit", testCategoryId))
                .andExpect(status().isOk())
                .andExpect(view().name("categories/form"))
                .andExpect(model().attributeExists("category"))
                .andExpect(model().attribute("category", testCategory))
                .andExpect(model().attribute("titulo", "Edit Category"));

        verify(categoryService, times(1)).findByIdEntity(testCategoryId);
    }

    @Test
    @DisplayName("POST /categories/{id} should update category and redirect")
    void shouldUpdateCategoryAndRedirect() throws Exception {
        // Given
        when(categoryService.saveEntity(any())).thenReturn(testCategory);

        // When/Then
        mockMvc.perform(post("/categories/{id}", testCategoryId.toString())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "Updated Category")
                .param("description", "Updated Description"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/categories"))
                .andExpect(flash().attributeExists("success"))
                .andExpect(flash().attribute("success", "Category updated successfully"));

        verify(categoryService, times(1)).saveEntity(any());
    }

    @Test
    @DisplayName("POST /categories/{id}/delete should delete category and redirect")
    void shouldDeleteCategoryAndRedirect() throws Exception {
        // Given
        doNothing().when(categoryService).deleteById(testCategoryId);

        // When/Then
        mockMvc.perform(post("/categories/{id}/delete", testCategoryId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/categories"))
                .andExpect(flash().attributeExists("success"))
                .andExpect(flash().attribute("success", "Category deleted successfully"));

        verify(categoryService, times(1)).deleteById(testCategoryId);
    }

    @Test
    @DisplayName("POST /categories/{id}/delete should handle ResourceNotFoundException")
    void shouldHandleResourceNotFoundExceptionOnDelete() throws Exception {
        // Given
        UUID nonExistingId = UUID.fromString("999e4567-e89b-12d3-a456-426614174999");
        doThrow(new ResourceNotFoundException("Category", "id", nonExistingId))
                .when(categoryService).deleteById(nonExistingId);

        // When/Then
        mockMvc.perform(post("/categories/{id}/delete", nonExistingId))
                .andExpect(status().isNotFound());

        verify(categoryService, times(1)).deleteById(nonExistingId);
    }
}