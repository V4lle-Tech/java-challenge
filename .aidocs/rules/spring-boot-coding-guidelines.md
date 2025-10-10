# Guía de Codificación Spring Boot - Mejores Prácticas para Aplicaciones Robustas

## Propósito
Esta guía consolida reglas y pautas para codificar en Spring Boot 3.x con Java 21, enfocada en robustez, separación de capas y mantenibilidad. Integra convenciones de inyección, DTOs, entidades, Value Objects para lógica compleja, manejo de excepciones, logging y configuración. Alineada con principios SOLID y DDD ligero para evitar contaminación de capas.

## Principios Generales
- **Separación de Capas**: Controladores manejan interfaz REST y mapeos DTO; servicios lógica de negocio pura con entidades/Value Objects; repositorios acceso a datos.
- **Robustez**: Validaciones tempranas, transacciones atómicas, excepciones custom, logging informativo.
- **Inmutabilidad**: Preferir Records para DTOs y Value Objects.
- **Evitar Contaminación**: Servicios no reciben/retornan DTOs; mapeos en controladores.

## Estructura de Paquetes
Basado en estándares, sin paquetes nuevos innecesarios:

```
src/
└── main/
    └── java/
        └── com.example.demo/
            ├── controller/    # REST endpoints, mapeos DTO
            ├── service/       # Lógica de negocio pura
            ├── repository/    # Acceso a datos
            ├── model/         # Entidades JPA y Value Objects
            ├── dto/           # DTOs inmutables
            ├── exception/     # Excepciones custom
            ├── config/        # Configuraciones
```

## Inyección de Dependencias
✅ Constructor injection con `@RequiredArgsConstructor`.  
❌ Field injection con `@Autowired`.

Ejemplo en Service:
```java
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
}
```

## DTOs
✅ Java Records inmutables con validaciones (@NotBlank, @Email).  
❌ Clases mutables con setters manuales.

Ejemplo:
```java
public record UserDTO(Long id, @NotBlank String name, @Email String email) {}
```

## Entidades JPA
✅ `@Getter`, `@Setter` selectivos, `@NoArgsConstructor`, `@AllArgsConstructor`, `@Builder`.  
✅ Integrar Value Objects para lógica compleja.  
❌ `@Data` (problemas con Hibernate).  
❌ `@ToString` en relaciones bidireccionales (riesgo de stack overflow).

Ejemplo:
```java
@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class User {
    @Id @GeneratedValue private Long id;
    @Column private String name;
}
```

## Value Objects para Lógica Compleja
✅ Usar en servicios para encapsular reglas de negocio puras (e.g., cálculos monetarios). Inmutables con validaciones en constructor.  
✅ Integrar en model/ para dominio.  
❌ Usar en capas externas (controladores o DTOs).

Ejemplo Money (Value Object):
```java
public record Money(BigDecimal amount) {
    public Money { if (amount.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException(); }
    public Money applyDiscount(Discount discount) { /* lógica */ }
}
```

## Flujo de Interacción entre Capas
Para evitar contaminación:
1. Controlador recibe payload, valida DTO request (@Valid).
2. Controlador mapea DTO a entidad/primitivas/Value Objects.
3. Servicio recibe entidad/primitivas, usa Value Objects para lógica compleja, persiste vía repositorio, retorna entidad.
4. Controlador mapea entidad a DTO response, envía ResponseEntity.

| Capa       | Responsabilidad                  | Recibe          | Retorna        |
|------------|----------------------------------|-----------------|----------------|
| Controlador| HTTP, validación, mapeo DTO    | DTO request    | DTO response  |
| Servicio  | Lógica negocio, transacciones   | Entidad/VO     | Entidad       |
| Repositorio| Persistencia                    | Entidad        | Entidad       |

Ejemplo en Controlador:
```java
@PostMapping
public ResponseEntity<UserDTO> create(@Valid @RequestBody CreateUserRequest dto) {
    User entity = /* mapeo */;
    User saved = service.create(entity);
    return ResponseEntity.ok(/* mapeo a DTO */);
}
```

Ejemplo en Servicio:
```java
@Transactional
public User create(User user) {
    // Lógica con Value Objects
    return repository.save(user);
}
```

## Manejo de Fechas
✅ `java.time.*` (LocalDateTime).  
❌ `java.util.Date`.

## Validación
✅ `@Valid` en controladores + Bean Validation en DTOs.  
✅ Validaciones de negocio en servicios.  
❌ Validación manual dispersa.

## Excepciones
✅ `@ControllerAdvice` para manejo global.  
✅ Excepciones custom (e.g., ResourceNotFoundException).  
❌ Catches genéricos.

Ejemplo:
```java
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleNotFound(EntityNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }
}
```

## REST API Design
✅ RESTful: GET /users, POST /users, ResponseEntity con status.  
❌ Endpoints no estándar (/getUser).

## Logging
✅ `@Slf4j` con niveles apropiados en services/controladores.  
❌ System.out.

## Configuración
✅ `@Value` o `@ConfigurationProperties`.  
✅ Perfiles (application-dev.yml).  
❌ Hardcoded values.

## Checklist de Codificación Robusta
- [ ] Capas separadas sin contaminación.
- [ ] DTOs inmutables con validaciones.
- [ ] Value Objects para lógica compleja en servicios.
- [ ] `@Transactional` en servicios.
- [ ] Mapeos en controladores.
- [ ] Excepciones manejadas globalmente.
- [ ] Logging informativo.

## Validación
- Verificar estilo: `mvn checkstyle:check`.
- Compilar: `mvn compile`.
- Dependencias: `mvn dependency:tree`.

**Última Actualización**: 2025-10-07  
**Versión**: 2.0.0