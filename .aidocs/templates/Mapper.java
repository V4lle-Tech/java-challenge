package com.example.demo.mapper;

import com.example.demo.dto.EntityDTO;
import com.example.demo.dto.CreateEntityRequest;
import com.example.demo.dto.UpdateEntityRequest;
import com.example.demo.model.Entity;
import org.springframework.stereotype.Component;

@Component
public class EntityMapper {

    // Entity to DTO
    public EntityDTO toDto(Entity entity) {
        return new EntityDTO(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getActive(),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }

    // Create Request to Entity
    public Entity toEntity(CreateEntityRequest request) {
        return Entity.builder()
                .name(request.name())
                .description(request.description())
                .active(true) // Default value
                .build();
    }

    // Update Request to Entity (partial update)
    public Entity updateEntity(UpdateEntityRequest request, Entity entity) {
        entity.setName(request.name());
        entity.setDescription(request.description());
        entity.setActive(request.active());
        return entity;
    }

    // Full update from DTO (si es necesario)
    public Entity updateFromDto(EntityDTO dto, Entity entity) {
        entity.setName(dto.name());
        entity.setDescription(dto.description());
        entity.setActive(dto.active());
        return entity;
    }
}