# Validation Guide - Bean Validation en Spring Boot

## Purpose
Guía para implementar validación robusta usando Bean Validation (JSR-303) en DTOs y entidades, integrada con Spring Boot.

## ✅ DO
- Usar `@Valid` en controllers para validar request bodies
- Aplicar constraints en DTOs (records recomendados)
- Usar `@Validated` en services para validación programática
- Crear grupos de validación cuando necesario
- Manejar errores con `@ControllerAdvice`

## ❌ DON'T
- No validar en services (usar DTOs validados)
- No usar validación manual (if/throw)
- No ignorar errores de validación

## Constraint Annotations

### Básicos
```java
public record CreateUserRequest(
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    String name,

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    String email,

    @NotNull(message = "Age is required")
    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 120, message = "Age must be less than 120")
    Integer age
) {}
```

### Avanzados
```java
public record UpdateProductRequest(
    @NotNull Long id,

    @NotBlank
    @Pattern(regexp = "^[A-Z][a-zA-Z0-9 ]*$", message = "Name must start with capital letter")
    String name,

    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Price format invalid")
    BigDecimal price,

    @Past(message = "Manufacture date must be in the past")
    LocalDate manufactureDate
) {}
```

## Controller Integration

### Request Body Validation
```java
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody CreateUserRequest request) {
        UserDTO user = userService.createUser(request);
        return ResponseEntity.created(URI.create("/api/users/" + user.id())).body(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequest request) {
        UserDTO user = userService.updateUser(id, request);
        return ResponseEntity.ok(user);
    }
}
```

### Path Variable Validation
```java
@GetMapping("/{id}")
public ResponseEntity<UserDTO> getUser(@PathVariable @Min(1) Long id) {
    return userService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
}
```

## Service Layer Validation

### Programmatic Validation
```java
@Service
@RequiredArgsConstructor
@Validated  // Habilita validación en métodos
public class UserService {
    private final Validator validator;
    private final UserRepository userRepository;

    @Transactional
    public UserDTO createUser(@Valid CreateUserRequest request) {
        // DTO ya validado por @Valid en controller

        // Validación adicional de negocio
        if (userRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("Email already exists");
        }

        User user = userMapper.toEntity(request);
        user = userRepository.save(user);
        return userMapper.toDto(user);
    }

    // Validación en métodos específicos
    @Transactional
    public void updateEmail(@NotNull Long userId, @Email String newEmail) {
        // Validación automática por @Email
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setEmail(newEmail);
        userRepository.save(user);
    }
}
```

## Custom Validators

### Email único
```java
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueEmailValidator.class)
public @interface UniqueEmail {
    String message() default "Email already exists";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
    private final UserRepository userRepository;

    @Autowired
    public UniqueEmailValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return email != null && !userRepository.existsByEmail(email);
    }
}
```

### Uso del custom validator
```java
public record CreateUserRequest(
    @NotBlank String name,
    @UniqueEmail String email  // Validación custom
) {}
```

## Error Handling

### Global Exception Handler
```java
@ControllerAdvice
@Slf4j
public class ValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolation(ConstraintViolationException ex) {
        return ResponseEntity.badRequest().body("Validation error: " + ex.getMessage());
    }
}
```

## Validation Groups

### Definir grupos
```java
public interface Create {}
public interface Update {}

public record UserDTO(
    @NotNull(groups = Update.class) Long id,
    @NotBlank(groups = {Create.class, Update.class}) String name,
    @NotBlank(groups = Create.class) @Email String email
) {}
```

### Uso selectivo
```java
@PostMapping
public ResponseEntity<UserDTO> create(@Validated(Create.class) @RequestBody UserDTO dto) {
    // Solo valida constraints del grupo Create
}

@PutMapping("/{id}")
public ResponseEntity<UserDTO> update(@Validated(Update.class) @RequestBody UserDTO dto) {
    // Solo valida constraints del grupo Update
}
```

## Testing Validation

### Controller Test
```java
@Test
void shouldReturn400_WhenInvalidData() throws Exception {
    CreateUserRequest invalidRequest = new CreateUserRequest("", "invalid-email");

    mockMvc.perform(post("/api/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(invalidRequest)))
        .andExpect(status().isBadRequest());
}
```

### Unit Test para Custom Validator
```java
@ExtendWith(MockitoExtension.class)
class UniqueEmailValidatorTest {
    @Mock private UserRepository userRepository;
    @InjectMocks private UniqueEmailValidator validator;

    @Test
    void shouldValidateUniqueEmail() {
        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);

        assertTrue(validator.isValid("test@example.com", null));
        verify(userRepository).existsByEmail("test@example.com");
    }
}
```

## Best Practices

1. **Siempre usar `@Valid` en controllers** para request bodies
2. **Preferir records para DTOs** - son inmutables y limpios
3. **Mensajes descriptivos** en constraints
4. **Validación en service layer** solo para lógica de negocio
5. **Manejo global de errores** con `@ControllerAdvice`
6. **Testing exhaustivo** de reglas de validación

## Common Pitfalls

- ❌ Olvidar `@Valid` en controllers
- ❌ Validación manual en services
- ❌ Constraints sin mensajes descriptivos
- ❌ No manejar errores de validación
- ❌ Validar entidades JPA (usar DTOs)

**Last Updated**: 2025-10-08
**Version**: 1.0.0