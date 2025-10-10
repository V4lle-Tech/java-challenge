package com.example.demo.service;

import com.example.demo.dto.CreateCategoryRequest;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Category;
import com.example.demo.repository.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for CategoryService
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("CategoryService - Unit Tests")
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    // ========== HAPPY PATH TESTS ==========

    @Test
    @DisplayName("Debe guardar categoría válida correctamente")
    void shouldSaveValidCategory() {
        // Given
        CreateCategoryRequest categoryRequest = new CreateCategoryRequest("Electronics", "Is a new category");
        Category savedCategory = createValidCategory();
        UUID savedId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        savedCategory.setId(savedId);

        when(categoryRepository.save(any(Category.class))).thenReturn(savedCategory);

        // When
        Category result = categoryService.save(categoryRequest);

        // Then
        assertNotNull(result);
        assertEquals(savedId, result.getId());
        assertEquals("Electronics", result.getName());

        verify(categoryRepository).save(any(Category.class));
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    @DisplayName("Debe encontrar categoría por ID existente")
    void shouldFindCategoryById_WhenExists() {
        // Given
        Category category = createValidCategory();
        UUID categoryId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        category.setId(categoryId);

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        // When
        Optional<Category> result = categoryService.findById(categoryId);

        // Then
        assertTrue(result.isPresent());
        assertEquals(categoryId, result.get().getId());
        assertEquals("Electronics", result.get().getName());

        verify(categoryRepository).findById(categoryId);
    }

    @Test
    @DisplayName("Debe retornar Optional.empty cuando categoría no existe")
    void shouldReturnEmptyOptional_WhenCategoryNotFound() {
        // Given
        UUID nonExistingId = UUID.fromString("999e4567-e89b-12d3-a456-426614174999");
        when(categoryRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        // When
        Optional<Category> result = categoryService.findById(nonExistingId);

        // Then
        assertFalse(result.isPresent());

        verify(categoryRepository).findById(nonExistingId);
    }

    @Test
    @DisplayName("Debe obtener categoría por ID y lanzarla correctamente")
    void shouldGetCategoryById_WhenExists() {
        // Given
        Category category = createValidCategory();
        UUID categoryId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        category.setId(categoryId);

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        // When
        Category result = categoryService.getById(categoryId);

        // Then
        assertNotNull(result);
        assertEquals(categoryId, result.getId());
        assertEquals("Electronics", result.getName());

        verify(categoryRepository).findById(categoryId);
    }

    @Test
    @DisplayName("Debe retornar todas las categorías")
    void shouldReturnAllCategories() {
        // Given
        Category category1 = createValidCategory();
        category1.setId(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));
        Category category2 = createValidCategory();
        category2.setId(UUID.fromString("456e7890-e89b-12d3-a456-426614174001"));
        category2.setName("Books");

        List<Category> categories = Arrays.asList(category1, category2);

        when(categoryRepository.findAll()).thenReturn(categories);

        // When
        List<Category> result = categoryService.findAll();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Electronics", result.get(0).getName());
        assertEquals("Books", result.get(1).getName());

        verify(categoryRepository).findAll();
    }

    @Test
    @DisplayName("Debe buscar categorías por nombre")
    void shouldSearchCategoriesByName() {
        // Given
        Category category = createValidCategory();
        category.setId(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));

        when(categoryRepository.findByNameContainingIgnoreCase("elect"))
                .thenReturn(Arrays.asList(category));

        // When
        List<Category> result = categoryService.searchByName("elect");

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Electronics", result.get(0).getName());

        verify(categoryRepository).findByNameContainingIgnoreCase("elect");
    }

    @Test
    @DisplayName("Debe verificar existencia de categoría por nombre")
    void shouldCheckCategoryExistsByName() {
        // Given
        when(categoryRepository.existsByName("Electronics")).thenReturn(true);

        // When
        boolean result = categoryService.existsByName("Electronics");

        // Then
        assertTrue(result);

        verify(categoryRepository).existsByName("Electronics");
    }

    @Test
    @DisplayName("Debe retornar conteo total de categorías")
    void shouldReturnTotalCategoryCount() {
        // Given
        when(categoryRepository.count()).thenReturn(5L);

        // When
        long result = categoryService.count();

        // Then
        assertEquals(5L, result);

        verify(categoryRepository).count();
    }

    // ========== EXCEPTION TESTS ==========

    @Test
    @DisplayName("Debe lanzar ResourceNotFoundException cuando categoría no existe al obtener por ID")
    void shouldThrowException_WhenGettingNonExistentCategory() {
        // Given
        UUID nonExistingId = UUID.fromString("999e4567-e89b-12d3-a456-426614174999");
        when(categoryRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> {
            categoryService.getById(nonExistingId);
        });

        verify(categoryRepository).findById(nonExistingId);
    }

    @Test
    @DisplayName("Debe lanzar ResourceNotFoundException al eliminar categoría inexistente")
    void shouldThrowException_WhenDeletingNonExistentCategory() {
        // Given
        UUID nonExistingId = UUID.fromString("999e4567-e89b-12d3-a456-426614174999");
        when(categoryRepository.existsById(nonExistingId)).thenReturn(false);

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> {
            categoryService.deleteById(nonExistingId);
        });

        verify(categoryRepository).existsById(nonExistingId);
        verify(categoryRepository, never()).deleteById(any(UUID.class));
    }

    // ========== EDGE CASES ==========

    @Test
    @DisplayName("Debe manejar búsqueda con string vacío")
    void shouldHandleEmptySearchString() {
        // Given
        when(categoryRepository.findByNameContainingIgnoreCase(""))
                .thenReturn(Arrays.asList());

        // When
        List<Category> result = categoryService.searchByName("");

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(categoryRepository).findByNameContainingIgnoreCase("");
    }

    @Test
    @DisplayName("Debe manejar búsqueda con null")
    void shouldHandleNullSearchString() {
        // Given
        when(categoryRepository.findByNameContainingIgnoreCase(null))
                .thenReturn(Arrays.asList());

        // When
        List<Category> result = categoryService.searchByName(null);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(categoryRepository).findByNameContainingIgnoreCase(null);
    }

    // ========== HELPER METHODS ==========

    private Category createValidCategory() {
        Category category = new Category();
        category.setName("Electronics");
        category.setDescription("Electronic devices and accessories");
        return category;
    }
}