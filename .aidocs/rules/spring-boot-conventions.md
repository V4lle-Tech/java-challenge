# Spring Boot Conventions - Guía Completa

## Inyección de Dependencias
✅ Constructor injection con `@RequiredArgsConstructor`
❌ Field injection con `@Autowired`

## DTOs
✅ Java Records para DTOs inmutables
❌ Clases con getters/setters manuales

## Entidades JPA
✅ `@Getter` y `@Setter` selectivos
✅ `@NoArgsConstructor` y `@AllArgsConstructor`
✅ `@Builder` para construcción flexible
❌ `@Data` (problemas con hashCode/equals en Hibernate)

## Manejo de Fechas
✅ `java.time.*` (LocalDateTime, etc.)
❌ `java.util.Date` o `java.sql.Timestamp`

## Validación
✅ `@Valid` en controllers + Bean Validation
✅ `@NotBlank`, `@Email`, etc. en DTOs
❌ Validación manual en services

## Excepciones
✅ `@ControllerAdvice` para manejo global
✅ Excepciones custom (ResourceNotFoundException)
❌ Exceptions genéricas sin contexto

## Testing Strategy
- **Unit Tests:** `@ExtendWith(MockitoExtension.class)` para services
- **Slice Tests:** `@WebMvcTest` para controllers, `@DataJpaTest` para repositories
- **Integration Tests:** `@SpringBootTest` para flujos end-to-end críticos

## REST API Design
✅ RESTful conventions (GET /users, POST /users)
✅ ResponseEntity<> con status codes apropiados
✅ `@RequestBody` para POST/PUT, `@PathVariable` para IDs
❌ Endpoints no estándar (/getUserById)

## Logging
✅ `@Slf4j` con niveles apropiados
✅ Logs informativos en services
❌ System.out.println

## Configuración
✅ `@Value` para valores simples
✅ `@ConfigurationProperties` para grupos
✅ Profiles (application-dev.yml, etc.)
❌ Hardcoded values en código
