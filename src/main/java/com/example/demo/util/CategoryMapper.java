package com.example.demo.util;

import com.example.demo.dto.CategoryDTO;
import com.example.demo.dto.CreateCategoryRequest;
import com.example.demo.dto.UpdateCategoryRequest;
import com.example.demo.model.Category;

/**
 * Mapper for Category entity and DTOs
 */
public class CategoryMapper {

    public static CategoryDTO toDto(Category category) {
        return new CategoryDTO(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getCreatedAt(),
                category.getUpdatedAt());
    }

    public static Category toEntity(CreateCategoryRequest request) {
        Category category = new Category();
        category.setName(request.name());
        category.setDescription(request.description());
        return category;
    }

    public static void updateEntityFromDto(Category category, UpdateCategoryRequest request) {
        if (request.name() != null) {
            category.setName(request.name());
        }
        if (request.description() != null) {
            category.setDescription(request.description());
        }
    }
}