package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "entities") // Cambiar por nombre de tabla apropiado
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private Boolean active = true;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // Relaciones de ejemplo (descomentar según necesidad)

    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;

    // @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval =
    // true)
    // private List<ChildEntity> children = new ArrayList<>();

    // Métodos de negocio si son necesarios

    public boolean isActive() {
        return Boolean.TRUE.equals(active);
    }

    public void deactivate() {
        this.active = false;
    }
}