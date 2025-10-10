# Guía de Testing Spring Boot - Mejores Prácticas para Aplicaciones Robustas

## Propósito
Consolida estrategias de testing para robustez: unitarios para lógica, slice para capas específicas, integration para flujos end-to-end. Integra plantillas de guías originales, ajustadas para Value Objects y flujo de capas.

## Principios
- **Cobertura**: Unit para services (lógica con Value Objects), slice para controllers/repositories, integration para críticos.
- **Robustez**: Limpieza de DB, verificaciones en DB, no mocks en integration.
- **Velocidad**: Slice/unit rápidos; integration lentos pero críticos.

## Unit Tests (para Services)
✅ `@ExtendWith(MockitoExtension.class)`, mock dependencias.  
✅ Cubrir happy path, exceptions, edge cases.

Plantilla:
```java
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock private UserRepository repo;
    @InjectMocks private UserService service;

    @Test void shouldCreateUser() {
        // Given, When, Then con asserts y verifies
    }
}
```

## Slice Tests
- **@WebMvcTest (Controllers)**: Mock services, test HTTP.
- **@DataJpaTest (Repositories)**: DB real de test, no mocks.

Plantilla @WebMvcTest:
```java
@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired private MockMvc mvc;
    @MockBean private UserService service;

    @Test void shouldCreateUser() throws Exception {
        mvc.perform(post("/users")).andExpect(status().isOk());
    }
}
```

Plantilla @DataJpaTest:
```java
@DataJpaTest
class UserRepositoryTest {
    @Autowired private UserRepository repo;
    @Autowired private TestEntityManager em;

    @Test void shouldSaveUser() {
        // Persist, flush, verify
    }
}
```

## Integration Tests (End-to-End)
✅ `@SpringBootTest` + `@AutoConfigureMockMvc` + `@ActiveProfiles("test")`.  
✅ Flujos críticos: HTTP → Service → DB.  
✅ Limpieza en @BeforeEach.

Plantilla:
```java
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserIntegrationTest {
    @Autowired private MockMvc mvc;
    @Autowired private UserRepository repo;

    @BeforeEach void setUp() { repo.deleteAll(); }

    @Test void shouldCompleteUserCreation() throws Exception {
        mvc.perform(post("/users")).andExpect(status().isCreated());
        // Verificar en DB
    }
}
```

## Checklist de Testing Robusto
- [ ] Unit: Cubre lógica con Value Objects.
- [ ] Slice: Capas aisladas (no contexto completo).
- [ ] Integration: Flujo end-to-end, verificación DB.
- [ ] Independientes: Limpieza entre tests.
- [ ] Nombres descriptivos: @DisplayName("Debe [acción] cuando [condición]").

## Validación
- Ejecutar: `mvn test -Dtest="*IntegrationTest"`.
- Perfiles: `application-test.yml` con H2 DB.
- Performance: < 30s por integration test.

**Última Actualización**: 2025-10-07  
**Versión**: 2.0.0