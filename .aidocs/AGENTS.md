# AGENTS.md - Guías del Proyecto Spring Boot

## Resumen del Proyecto
Aplicación Spring Boot 3.x con Java 21, Maven y soporte para devcontainer en desarrollo asistido por IA.

## Instrucciones Críticas para Agentes AI
- Consultar guias de desarrollo en `.aidoc/rules/`

### ⚠️ OBLIGATORIO - Leer Antes de Cualquier Tarea

1. **NO generar archivos de documentación** tras finalizar tareas.
2. **NO incluir comentarios inline extensos** que expliquen código evidente.
3. **NO crear archivos fuera de la estructura definida**.
4. **NO modificar pom.xml** sin autorización explícita.
5. **SIEMPRE ejecutar pruebas de archivo individual** en lugar de la suite completa.

### Stack Tecnológico
- Java 21 LTS
- Spring Boot 3.2.0
- Maven 3.9+
- Base de datos H2 (desarrollo)
- PostgreSQL (producción)
- Anotaciones Lombok
- JUnit 5 + Mockito

### Estructura del Proyecto
```
src/
├── main/
│   ├── java/com/example/demo/
│   │   ├── controller/      # REST endpoints
│   │   ├── service/         # Business logic  
│   │   ├── repository/      # Data access
│   │   ├── model/           # JPA entities
│   │   ├── dto/             # Data transfer (use Records)
│   │   ├── exception/       # Custom exceptions
│   │   ├── config/          # Configuration classes
│   │   └── util/            # Utilities
│   └── resources/
│       ├── application.yml  # Main configuration
│       └── application-{profile}.yml
└── test/                     # Mirror main structure
```

## Comandos Esenciales

### Build & Run (Operaciones de Archivo Único)
```bash
# Verificación de tipos en archivo único (RÁPIDO - usar esto)
mvn compile -pl . -Djavac.source.files=src/main/java/path/to/File.java

# Ejecutar test único (RÁPIDO - usar esto)
mvn test -Dtest=UserServiceTest#testCreateUser

# Formatear archivo único
mvn spotless:apply -DspotlessFiles=src/main/java/path/to/File.java

# Build completo (SOLO con permiso)
mvn clean install
```

### Flujo de Desarrollo
```bash
# Iniciar aplicación
mvn spring-boot:run

# Ejecutar con perfil
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Modo debug
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005"
```

## Estándares de Codificación

### ✅ SÍ HACER
- Usar inyección por constructor: `private final UserService userService;`
- Usar Java Records para DTOs: `public record UserDTO(Long id, String name) {}`
- Usar `@RestController` para APIs, retornar `ResponseEntity<>`
- Aplicar `@Transactional` en la capa de servicio.
- Usar `@Valid` para validar solicitudes.
- Colocar la clase principal en el paquete raíz: `com.example.demo`.
- Seguir convenciones RESTful: GET /users, POST /users, GET /users/{id}.
- Usar `Optional<>` para retornos de repositorios.
- Manejar excepciones con `@ControllerAdvice`.

### ❌ NO HACER
- No usar inyección por campo con `@Autowired`.
- No retornar entidades directamente desde controladores.
- No incluir lógica de negocio en controladores.
- No crear clases genéricas `Utils`.
- No usar `java.util.Date` - usar `java.time.*`.
- No codificar valores fijos - usar `@Value` o `@ConfigurationProperties`.
- No escribir SQL directo en repositorios - usar JPQL o nombres de métodos.
- No crear archivos sin verificar si existen similares.

## Patrones Comunes

### Controller Example
```java
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        return userService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
```

### Service Example
```java
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public UserDTO createUser(@Valid CreateUserRequest request) {
        User user = userMapper.toEntity(request);
        user = userRepository.save(user);
        return userMapper.toDto(user);
    }
}
```

### Entity Example
```java
@Entity
@Table(name = "users")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
```

### DTO Example (Use Records)
```java
public record UserDTO(
    Long id,
    String email,
    String name,
    LocalDateTime createdAt
) {}

public record CreateUserRequest(
    @NotBlank @Email String email,
    @NotBlank @Size(min = 2, max = 100) String name
) {}
```

## Guías de Pruebas

### Unit Test Pattern
```java
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldCreateUser() {
        // Given
        CreateUserRequest request = new CreateUserRequest("test@example.com", "Test User");
        User savedUser = User.builder().id(1L).email(request.email()).build();
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // When
        UserDTO result = userService.createUser(request);

        // Then
        assertThat(result.id()).isEqualTo(1L);
        verify(userRepository).save(any(User.class));
    }
}
```

## Permisos de Seguridad

### ✅ Permitido Sin Consultar
- Leer cualquier archivo del proyecto.
- Listar contenido de directorios.
- Compilar un solo archivo Java.
- Ejecutar una prueba unitaria.
- Formatear código.
- Agregar sentencias de log.

### ⚠️ Consultar Antes de Hacer
- Instalar nuevas dependencias.
- Modificar `pom.xml`.
- Crear nuevos paquetes.
- Eliminar archivos.
- Ejecutar suite completa de pruebas.
- Hacer push a git.

### 🚫 Nunca Sin Permiso Explícito
- Modificar flujos de trabajo en `.github/`.
- Cambiar esquemas de base de datos.
- Eliminar datos de prueba.
- Modificar configuraciones de seguridad.
- Acceder a configuraciones de producción.

## Consejos de Optimización de Tokens

1. Usar operaciones de archivo único en lugar de builds completos.
2. Referenciar plantillas en lugar de generar desde cero.
3. No incluir contenido completo de archivos en respuestas.
4. Usar nombres de variables concisos en ejemplos.
5. Evitar repetir bloques de código extensos.

## Al Crear Nuevos Archivos

Antes de crear CUALQUIER archivo nuevo:
1. Verificar si existe un archivo similar: `find . -name "*Similar*"`.
2. Confirmar paquete/directorio correcto.
3. Seguir convención de nombres de archivos existentes.
4. Usar plantilla adecuada desde `.aidocs/templates/`.
5. Agregar a índices/configuraciones relevantes si es necesario.

## Variables de Entorno

Requeridas para desarrollo:
```properties
SPRING_PROFILES_ACTIVE=dev
DATABASE_URL=jdbc:h2:mem:testdb
JWT_SECRET=${JWT_SECRET:default-dev-secret-change-in-production}
```

## Referencia Rápida

- Clase principal: `src/main/java/com/example/demo/Application.java`
- Configuración: `src/main/resources/application.yml`
- Pruebas: Estructura espejo en `src/test/`
- Puerto: 8080 (por defecto)
- Consola H2: http://localhost:8080/h2-console (solo desarrollo)
- Actuator: http://localhost:8080/actuator/health