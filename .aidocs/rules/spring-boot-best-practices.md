# Mejores Prácticas Spring Boot

## Propósito
Consolidar mejores prácticas de Spring Boot 3.x para arquitectura en capas, diseño RESTful, manejo de excepciones y manejo de fecha/hora, alineadas con AGENTS.md y spring-boot-conventions.md.

## Arquitectura en Capas
- **✅ HACER**: Separar responsabilidades: controllers (endpoints REST), services (lógica de negocio), repositories (acceso a datos).
  ```java
  // Controller
  @RestController
  @RequestMapping("/api/users")
  public class UserController {
      private final UserService userService;
  }
  ```
- **❌ NO HACER**: Colocar lógica de negocio en controllers o repositories.

## Diseño RESTful
- **✅ HACER**: Seguir convenciones REST: GET `/users`, POST `/users`, GET `/users/{id}`.
  ```java
  @GetMapping("/{id}")
  public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
      return userService.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }
  ```
- **❌ NO HACER**: Usar endpoints no estándar (ej: `/getUserById`).

## Manejo de Excepciones
- **✅ HACER**: Usar `@ControllerAdvice` para manejo centralizado de excepciones.
  ```java
  @ControllerAdvice
  public class GlobalExceptionHandler {
      @ExceptionHandler(EntityNotFoundException.class)
      public ResponseEntity<String> handleNotFound(EntityNotFoundException ex) {
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
      }
  }
  ```
- **❌ NO HACER**: Manejar excepciones en controllers; evitar catches genéricos de `Exception`.

## Manejo de Fecha/Hora
- **✅ HACER**: Usar `java.time.*` (ej: `LocalDateTime`) para campos de fecha/hora.
  ```java
  @Column
  private LocalDateTime createdAt;
  ```
- **❌ NO HACER**: Usar `java.util.Date` o `java.sql.Timestamp`.

## Additional Best Practices

### Security
- **✅ HACER**: Usar Spring Security para autenticación/autorización
- **✅ HACER**: Validar inputs para prevenir injection attacks
- **✅ HACER**: Usar HTTPS en producción
- **❌ NO HACER**: Exponer datos sensibles en logs
- **❌ NO HACER**: Hardcodear credenciales

### Performance
- **✅ HACER**: Usar pagination para listas grandes
- **✅ HACER**: Implementar caching cuando apropiado (@Cacheable)
- **✅ HACER**: Optimizar queries N+1 con fetch joins
- **❌ NO HACER**: Cargar entidades completas innecesariamente
- **❌ NO HACER**: Ejecutar queries en loops

### Database
- **✅ HACER**: Usar índices en campos de búsqueda frecuentes
- **✅ HACER**: Implementar constraints a nivel DB
- **✅ HACER**: Usar migrations (Flyway/Liquibase)
- **❌ NO HACER**: Modificar schema manualmente
- **❌ NO HACER**: Usar queries nativas sin necesidad

### API Design
- **✅ HACER**: Versionar APIs (/api/v1/users)
- **✅ HACER**: Usar HATEOAS para APIs REST maduras
- **✅ HACER**: Documentar con OpenAPI/Swagger
- **❌ NO HACER**: Cambiar contratos sin versioning
- **❌ NO HACER**: Exponer internals del dominio

### Monitoring & Observability
- **✅ HACER**: Configurar Actuator endpoints
- **✅ HACER**: Agregar métricas custom con Micrometer
- **✅ HACER**: Usar logging estructurado
- **❌ NO HACER**: Ignorar logs de error
- **❌ NO HACER**: Exponer endpoints sensibles en prod

## Guías de Calidad de Código

### Convenciones de Nomenclatura
- **Clases**: PascalCase (UserService, ProductController)
- **Métodos**: camelCase (findById, createUser)
- **Constantes**: UPPER_SNAKE_CASE (MAX_RETRY_COUNT)
- **Paquetes**: lowercase (com.example.demo)

### Estructura de Paquetes
```
com.example.demo
├── controller/     # Endpoints REST
├── service/        # Lógica de negocio
├── repository/     # Acceso a datos
├── model/          # Entidades JPA
├── dto/            # Objetos de transferencia de datos
├── exception/      # Excepciones custom
├── config/         # Clases de configuración
└── util/           # Utilidades (minimal)
```

### Mensajes de Commit
- **✅ HACER**: Mensajes descriptivos en inglés
- **✅ HACER**: Referenciar issues/tickets
- **❌ NO HACER**: Mensajes genéricos ("fix", "update")

## Flujo de Desarrollo

### Antes de Commitear
1. Ejecutar tests: `mvn test`
2. Verificar estilo: `mvn checkstyle:check`
3. Formatear código: `mvn spotless:apply`
4. Actualizar documentación si es necesario

### Code Reviews
- **✅ HACER**: Revisar lógica de negocio
- **✅ HACER**: Verificar tests adecuados
- **✅ HACER**: Validar cumplimiento de estándares
- **❌ NO HACER**: Aprobar código sin entenderlo

## Guías
- Usar inyección por constructor con `@RequiredArgsConstructor` (spring-boot-conventions.md).
- Aplicar `@Transactional` en capa de servicio (AGENTS.md).
- Evitar modificar `pom.xml` sin permiso (AGENTS.md).
- Usar `@Valid` para validación de requests en controllers.
- Verificar archivos existentes antes de crear nuevos: `find . -name "*Service*"`.

## Validación
- Ejecutar `mvn test` para tests de archivo único.
- Verificar estilo: `mvn checkstyle:check`.

**Última Actualización**: 2025-10-08
**Versión**: 1.0.0