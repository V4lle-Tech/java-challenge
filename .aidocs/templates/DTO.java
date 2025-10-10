// DTOs usando Records (recomendado)
public record EntityDTO(
        Long id,
        String name,
        String description,
        Boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
}

// Request DTOs con validación
public record CreateEntityRequest(
        @NotBlank(message = "Name is required") @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters") String name,

        @Size(max = 500, message = "Description must not exceed 500 characters") String description) {
}

public record UpdateEntityRequest(
        @NotBlank(message = "Name is required") @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters") String name,

        @Size(max = 500, message = "Description must not exceed 500 characters") String description,

        @NotNull(message = "Active status is required") Boolean active) {
}

// Response con enlaces HATEOAS (opcional)
public record EntityResponse(
        Long id,
        String name,
        String description,
        Boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        List<Link> links // Para HATEOAS
) {
    public static EntityResponse from(Entity entity) {
        return new EntityResponse(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.isActive(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                List.of(
                        linkTo(methodOn(EntityController.class).getById(entity.getId())).withSelfRel(),
                        linkTo(methodOn(EntityController.class).update(entity.getId(), null)).withRel("update"),
                        linkTo(methodOn(EntityController.class).delete(entity.getId())).withRel("delete")));
    }
}