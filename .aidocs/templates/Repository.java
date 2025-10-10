package com.example.demo.repository;

import com.example.demo.model.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EntityRepository extends JpaRepository<Entity, Long> {

    // Queries por campos únicos
    Optional<Entity> findByName(String name);

    boolean existsByName(String name);

    // Queries con múltiples condiciones
    List<Entity> findByActiveTrue();

    List<Entity> findByNameContainingIgnoreCase(String namePart);

    // Queries personalizadas con @Query
    @Query("SELECT e FROM Entity e WHERE e.active = true AND e.createdAt > :since")
    List<Entity> findActiveEntitiesCreatedAfter(@Param("since") LocalDateTime since);

    @Query("SELECT COUNT(e) FROM Entity e WHERE e.active = :active")
    long countByActive(@Param("active") boolean active);

    // Queries con joins (descomentar si hay relaciones)
    // @Query("SELECT e FROM Entity e JOIN e.parent p WHERE p.id = :parentId")
    // List<Entity> findByParentId(@Param("parentId") Long parentId);

    // Métodos de eliminación lógica
    // void deleteByName(String name); // Para eliminación física

    // Para soft delete, usar queries custom
    // @Query("UPDATE Entity e SET e.active = false WHERE e.id = :id")
    // @Modifying
    // void softDeleteById(@Param("id") Long id);
}