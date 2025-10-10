# Uso de Lombok - Spring Boot 3.x con Java 21

## Propósito
Guía para usar anotaciones Lombok en Spring Boot para reducir boilerplate mientras se mantienen mejores prácticas, alineadas con estándares de AGENTS.md.

## ✅ HACER
- Usar `@RequiredArgsConstructor` para inyección por constructor en services/controllers.
- Aplicar `@Builder` en entidades para creación flexible de objetos.
- Usar `@Getter`/`@Setter` en entidades para acceso controlado.
- Usar `@NoArgsConstructor` y `@AllArgsConstructor` para entidades JPA.
- Aplicar `@Value` para DTOs Records inmutables.

## ❌ NO HACER
- Evitar `@Data` en entidades JPA (hashCode/equals puede causar problemas con Hibernate).
- No usar `@Autowired` con Lombok; preferir inyección por constructor.
- Evitar `@ToString` en entidades con relaciones bidireccionales (riesgo de stack overflow).
- No aplicar `@Setter` en DTOs inmutables.

## Ejemplos

### Entidad JPA
```java
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor  // Requerido por JPA
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
```

### Service con Inyección por Constructor
```java
@Service
@RequiredArgsConstructor  // Genera constructor con todos los fields final
public class UserService {
    private final UserRepository userRepository;
    private final EmailService emailService;

    // No need for @Autowired
}
```

### DTO Record (Recomendado)
```java
@Value  // Lombok @Value para records
public record UserDTO(
    Long id,
    String name,
    String email,
    LocalDateTime createdAt
) {}

public record CreateUserRequest(
    @NotBlank String name,
    @NotBlank @Email String email
) {}
```

### Controller
```java
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j  // Para logging
public class UserController {
    private final UserService userService;
}
```

## Antipatrones a Evitar

### ❌ NO usar @Data en entidades
```java
@Entity
@Data  // ❌ Problemas con equals/hashCode en Hibernate
public class User {
    // ...
}
```

### ❌ NO usar @ToString en entidades con relaciones
```java
@Entity
@ToString  // ❌ Stack overflow con relaciones bidireccionales
public class Order {
    @ManyToOne
    private User user;  // User también tiene @ToString
}
```

### ✅ Solución: @ToString.Exclude
```java
@Entity
@Getter
@Setter
@ToString(exclude = "user")  // ✅ Excluir relaciones
public class Order {
    @ManyToOne
    private User user;
}
```