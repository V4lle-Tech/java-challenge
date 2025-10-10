package com.example.demo.repository;

import com.example.demo.model.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * DataJpaTest for CategoryRepository
 */
@DataJpaTest
@DisplayName("CategoryRepository - Data Layer Tests")
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    // ========== CRUD BÁSICO ==========

    @Test
    @DisplayName("Debe guardar y recuperar categoría correctamente")
    void shouldSaveAndRetrieveCategory() {
        // Given
        Category category = createValidCategory();

        // When
        Category saved = repository.save(category);
        entityManager.flush();
        entityManager.clear(); // Limpiar cache de primer nivel

        // Then
        assertNotNull(saved.getId());

        Optional<Category> found = repository.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals(category.getName(), found.get().getName());
        assertEquals(category.getDescription(), found.get().getDescription());
    }

    @Test
    @DisplayName("Debe eliminar categoría correctamente")
    void shouldDeleteCategory() {
        // Given
        Category category = entityManager.persistAndFlush(createValidCategory());
        UUID id = category.getId();

        // When
        repository.deleteById(id);
        entityManager.flush();

        // Then
        Optional<Category> found = repository.findById(id);
        assertFalse(found.isPresent());
    }

    // ========== CUSTOM QUERIES ==========

    @Test
    @DisplayName("Debe encontrar categorías por nombre que contiene")
    void shouldFindCategoriesByNameContaining() {
        // Given
        Category category1 = createValidCategory();
        category1.setName("Electronics");
        Category category2 = createValidCategory();
        category2.setName("Electronic Books");
        Category category3 = createValidCategory();
        category3.setName("Books");

        entityManager.persist(category1);
        entityManager.persist(category2);
        entityManager.persist(category3);
        entityManager.flush();

        // When
        List<Category> results = repository.findByNameContainingIgnoreCase("elect");

        // Then
        assertEquals(2, results.size());
        assertTrue(results.stream()
                .allMatch(c -> c.getName().toLowerCase().contains("elect")));
    }

    @Test
    @DisplayName("Debe encontrar categoría por nombre exacto")
    void shouldFindCategoryByExactName() {
        // Given
        Category category = createValidCategory();
        category.setName("Electronics");
        entityManager.persistAndFlush(category);

        // When
        Optional<Category> found = repository.findByName("Electronics");

        // Then
        assertTrue(found.isPresent());
        assertEquals("Electronics", found.get().getName());
    }

    @Test
    @DisplayName("Debe verificar existencia por nombre")
    void shouldCheckExistsByName() {
        // Given
        Category category = createValidCategory();
        category.setName("Electronics");
        entityManager.persistAndFlush(category);

        // When & Then
        assertTrue(repository.existsByName("Electronics"));
        assertFalse(repository.existsByName("NonExistent"));
    }

    @Test
    @DisplayName("Debe retornar lista vacía cuando no hay coincidencias en búsqueda")
    void shouldReturnEmptyListWhenNoMatches() {
        // When
        List<Category> results = repository.findByNameContainingIgnoreCase("nonexistent");

        // Then
        assertTrue(results.isEmpty());
    }

    @Test
    @DisplayName("Debe retornar Optional.empty cuando nombre no existe")
    void shouldReturnEmptyOptionalWhenNameNotFound() {
        // When
        Optional<Category> found = repository.findByName("NonExistent");

        // Then
        assertFalse(found.isPresent());
    }

    // ========== OPERACIONES CRUD COMPLETAS ==========

    @Test
    @DisplayName("Debe actualizar categoría correctamente")
    void shouldUpdateCategory() {
        // Given
        Category category = entityManager.persistAndFlush(createValidCategory());
        UUID id = category.getId();

        // When
        Category toUpdate = repository.findById(id).get();
        toUpdate.setName("Updated Electronics");
        toUpdate.setDescription("Updated description");
        repository.save(toUpdate);
        entityManager.flush();
        entityManager.clear();

        // Then
        Category updated = repository.findById(id).get();
        assertEquals("Updated Electronics", updated.getName());
        assertEquals("Updated description", updated.getDescription());
    }

    @Test
    @DisplayName("Debe manejar búsqueda case insensitive")
    void shouldHandleCaseInsensitiveSearch() {
        // Given
        Category category = createValidCategory();
        category.setName("Electronics");
        entityManager.persistAndFlush(category);

        // When
        List<Category> results = repository.findByNameContainingIgnoreCase("ELECTRONICS");

        // Then
        assertEquals(1, results.size());
        assertEquals("Electronics", results.get(0).getName());
    }

    // ========== HELPER METHODS ==========

    private Category createValidCategory() {
        Category category = new Category();
        category.setName("Test Category");
        category.setDescription("Test description");
        return category;
    }
}