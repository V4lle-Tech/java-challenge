package com.example.demo.util;

import com.example.demo.dto.CreateProductRequest;
import com.example.demo.dto.ProductDTO;
import com.example.demo.dto.UpdateProductRequest;
import com.example.demo.model.Category;
import com.example.demo.model.Product;

import java.util.Optional;
import java.util.UUID;

/**
 * Mapper for Product entity and DTOs
 */
public class ProductMapper {

    public static ProductDTO toDto(Product product) {
        UUID categoryId = null;
        String categoryName = null;
        if (product.getCategory() != null) {
            categoryId = product.getCategory().getId();
            categoryName = product.getCategory().getName();
        }

        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getImageUrl(),
                categoryId,
                categoryName,
                product.getActive(),
                product.getCreatedAt(),
                product.getUpdatedAt());
    }

    public static Product toEntity(CreateProductRequest request, Optional<Category> category) {
        Product product = new Product();
        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setStock(request.stock() != null ? request.stock() : 0);
        product.setImageUrl(request.imageUrl());
        product.setCategory(category.orElse(null));
        product.setActive(true);
        return product;
    }

    public static void updateEntityFromDto(Product product, UpdateProductRequest request, Optional<Category> category) {
        if (request.name() != null) {
            product.setName(request.name());
        }
        if (request.description() != null) {
            product.setDescription(request.description());
        }
        if (request.price() != null) {
            product.setPrice(request.price());
        }
        if (request.stock() != null) {
            product.setStock(request.stock());
        }
        if (request.imageUrl() != null) {
            product.setImageUrl(request.imageUrl());
        }
        if (category.isPresent()) {
            product.setCategory(category.get());
        }
        if (request.active() != null) {
            product.setActive(request.active());
        }
    }
}