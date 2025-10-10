# Slice Testing Guide - Layer-Specific Tests

Esta guía se enfoca en tests de "rebanadas" (slices) que prueban capas específicas de Spring Boot con contexto limitado pero real.

## 📋 Contexto del Proyecto

**Stack Tecnológico:**
- Java 21 (LTS)
- Spring Boot 3.x
- JUnit 5 + Spring Boot Test Slices
- H2/PostgreSQL para tests

**Estructura del Proyecto:**
```
src/
├── main/
│   ├── java/com/example/demo/
│   │   ├── controller/      # @WebMvcTest - HTTP layer
│   │   ├── service/         # NO slice test (usa unit tests)
│   │   ├── repository/      # @DataJpaTest - Data layer
│   │   ├── model/           # JPA entities
│   │   └── config/          # @TestConfiguration si necesario
│   └── resources/
│       ├── application.yml  # Main configuration
│       └── application-test.yml  # Test overrides
└── test/                     # Mirror main structure
```

---

## 🎯 Propósito de los Tests de Slice

Los tests de slice prueban **CAPAS ESPECÍFICAS** con contexto Spring real pero limitado:
- **@WebMvcTest:** Solo controllers + configuración web
- **@DataJpaTest:** Solo repositories + JPA + DB
- **@JsonTest:** Solo serialización JSON
- **Más rápidos que @SpringBootTest completo**
- **Más realistas que tests unitarios puros**

---

## 📝 Templates por Tipo de Slice Test

### 🕸️ TEST DE CONTROLLER (@WebMvcTest)

**Cuándo usar:** Para cualquier clase `@Service` con lógica de negocio.

**Template obligatorio:**

```java
package com.example.demo.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("NombreServicio - Unit Tests")
class NombreServicioTest {
    
    @Mock
    private DependenciaRepository dependenciaRepository;
    
    @Mock
    private OtraDeependencia otraDependencia;
    
    @InjectMocks
    private NombreServicio servicioUnderTest;
    
    // ========== HAPPY PATH TESTS ==========
    
    @Test
    @DisplayName("Debe [acción] cuando [condición]")
    void shouldDoSomething_WhenConditionMet() {
        // Given (Arrange)
        TipoDato input = crearInputValido();
        TipoDato expectedOutput = crearOutputEsperado();
        
        when(dependenciaRepository.metodo(any())).thenReturn(expectedOutput);
        
        // When (Act)
        TipoDato result = servicioUnderTest.metodoAProbar(input);
        
        // Then (Assert)
        assertNotNull(result);
        assertEquals(expectedOutput.getCampo(), result.getCampo());
        
        verify(dependenciaRepository).metodo(any());
        verify(dependenciaRepository, times(1)).metodo(any());
    }
    
    // ========== EXCEPTION TESTS ==========
    
    @Test
    @DisplayName("Debe lanzar [Excepción] cuando [condición de error]")
    void shouldThrowException_WhenErrorCondition() {
        // Given
        TipoDato invalidInput = crearInputInvalido();
        when(dependenciaRepository.metodo(any())).thenReturn(Optional.empty());
        
        // When & Then
        assertThrows(CustomException.class, () -> {
            servicioUnderTest.metodoAProbar(invalidInput);
        });
        
        verify(dependenciaRepository).metodo(any());
        verify(dependenciaRepository, never()).save(any());
    }
    
    // ========== EDGE CASES ==========
    
    @Test
    @DisplayName("Debe manejar entrada nula correctamente")
    void shouldHandleNullInput() {
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            servicioUnderTest.metodoAProbar(null);
        });
    }
    
    @Test
    @DisplayName("Debe manejar lista vacía correctamente")
    void shouldHandleEmptyList() {
        // Given
        List<TipoDato> emptyList = Collections.emptyList();
        
        // When
        List<TipoDato> result = servicioUnderTest.procesarLista(emptyList);
        
        // Then
        assertTrue(result.isEmpty());
    }
    
    // ========== HELPER METHODS ==========
    
    private TipoDato crearInputValido() {
        TipoDato dato = new TipoDato();
        dato.setCampo1("valor1");
        dato.setCampo2(100);
        return dato;
    }
    
    private TipoDato crearInputInvalido() {
        return new TipoDato(); // campos requeridos vacíos
    }
}
```

**Checklist obligatorio para @WebMvcTest:**
- [ ] Usa @WebMvcTest(NombreController.class)
- [ ] Mockea services con @MockBean
- [ ] Testea todos los endpoints HTTP
- [ ] Verifica status codes y JSON responses
- [ ] Incluye validaciones @Valid
- [ ] NO carga base de datos real

---

### 🗄️ TEST DE REPOSITORY (@DataJpaTest)

**Cuándo usar:** Para interfaces `@Repository` que extienden `JpaRepository`.

**Template obligatorio:**

```java
package com.example.demo.repository;

import com.example.demo.model.Entidad;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("NombreRepository - Data Layer Tests")
class NombreRepositoryTest {
    
    @Autowired
    private NombreRepository repository;
    
    @Autowired
    private TestEntityManager entityManager;
    
    // ========== CRUD BÁSICO ==========
    
    @Test
    @DisplayName("Debe guardar y recuperar entidad correctamente")
    void shouldSaveAndRetrieveEntity() {
        // Given
        Entidad entidad = crearEntidadValida();
        
        // When
        Entidad saved = repository.save(entidad);
        entityManager.flush();
        entityManager.clear(); // Limpiar cache de primer nivel
        
        // Then
        assertNotNull(saved.getId());
        
        Optional<Entidad> found = repository.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals(entidad.getCampo(), found.get().getCampo());
    }
    
    @Test
    @DisplayName("Debe eliminar entidad correctamente")
    void shouldDeleteEntity() {
        // Given
        Entidad entidad = entityManager.persistAndFlush(crearEntidadValida());
        Long id = entidad.getId();
        
        // When
        repository.deleteById(id);
        entityManager.flush();
        
        // Then
        Optional<Entidad> found = repository.findById(id);
        assertFalse(found.isPresent());
    }
    
    // ========== CUSTOM QUERIES ==========
    
    @Test
    @DisplayName("Debe encontrar entidades por [criterio]")
    void shouldFindEntitiesByCriteria() {
        // Given
        Entidad entidad1 = crearEntidadCon("valorBuscado");
        Entidad entidad2 = crearEntidadCon("otroValor");
        Entidad entidad3 = crearEntidadCon("valorBuscado");
        
        entityManager.persist(entidad1);
        entityManager.persist(entidad2);
        entityManager.persist(entidad3);
        entityManager.flush();
        
        // When
        List<Entidad> results = repository.findByCampo("valorBuscado");
        
        // Then
        assertEquals(2, results.size());
        assertTrue(results.stream()
            .allMatch(e -> "valorBuscado".equals(e.getCampo())));
    }
    
    @Test
    @DisplayName("Debe retornar lista vacía cuando no hay coincidencias")
    void shouldReturnEmptyListWhenNoMatches() {
        // When
        List<Entidad> results = repository.findByCampo("noExiste");
        
        // Then
        assertTrue(results.isEmpty());
    }
    
    // ========== RELACIONES ==========
    
    @Test
    @DisplayName("Debe cargar relaciones correctamente")
    void shouldLoadRelationships() {
        // Given
        Entidad padre = crearEntidadValida();
        EntidadRelacionada hijo = crearHijo();
        padre.setHijo(hijo);
        
        Entidad saved = entityManager.persistAndFlush(padre);
        entityManager.clear();
        
        // When
        Optional<Entidad> found = repository.findById(saved.getId());
        
        // Then
        assertTrue(found.isPresent());
        assertNotNull(found.get().getHijo());
        assertEquals(hijo.getCampo(), found.get().getHijo().getCampo());
    }
    
    // ========== HELPER METHODS ==========
    
    private Entidad crearEntidadValida() {
        Entidad entidad = new Entidad();
        entidad.setCampo1("valor1");
        entidad.setCampo2(100);
        return entidad;
    }
    
    private Entidad crearEntidadCon(String valor) {
        Entidad entidad = crearEntidadValida();
        entidad.setCampo(valor);
        return entidad;
    }
}
```

**Checklist obligatorio para @DataJpaTest:**
- [ ] Usa @DataJpaTest
- [ ] Testea operaciones CRUD y queries personalizadas
- [ ] Usa TestEntityManager para setup de datos
- [ ] Verifica resultados contra DB real (H2)
- [ ] Incluye flush() y clear() apropiadamente
- [ ] NO mockea el repository bajo test

**Cuándo usar:** Solo para flujos end-to-end críticos.

**Template obligatorio:**

```java
package com.example.demo.integration;

import com.example.demo.dto.EntidadDTO;
import com.example.demo.repository.EntidadRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Entidad Integration Tests - E2E Flow")
class EntidadIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private EntidadRepository repository;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @BeforeEach
    void setUp() {
        repository.deleteAll(); // Limpiar antes de cada test
    }
    
    @Test
    @DisplayName("Flujo completo: Crear, Leer, Actualizar y Eliminar entidad")
    void shouldCompleteFullCRUDLifecycle() throws Exception {
        // 1. CREATE - POST /api/entidades
        EntidadDTO createDto = new EntidadDTO("Nueva Entidad", "descripcion");
        
        MvcResult createResult = mockMvc.perform(post("/api/entidades")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDto)))
            .andExpect(status().isCreated())
            .andReturn();
        
        EntidadDTO created = objectMapper.readValue(
            createResult.getResponse().getContentAsString(),
            EntidadDTO.class
        );
        
        assertNotNull(created.getId());
        assertEquals("Nueva Entidad", created.getNombre());
        
        // Verificar en base de datos
        assertTrue(repository.existsById(created.getId()));
        
        // 2. READ - GET /api/entidades/{id}
        mockMvc.perform(get("/api/entidades/{id}", created.getId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nombre").value("Nueva Entidad"));
        
        // 3. UPDATE - PUT /api/entidades/{id}
        EntidadDTO updateDto = new EntidadDTO("Entidad Actualizada", "nueva desc");
        
        mockMvc.perform(put("/api/entidades/{id}", created.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nombre").value("Entidad Actualizada"));
        
        // 4. DELETE - DELETE /api/entidades/{id}
        mockMvc.perform(delete("/api/entidades/{id}", created.getId()))
            .andExpect(status().isNoContent());
        
        // Verificar que fue eliminado
        assertFalse(repository.existsById(created.getId()));
    }
}
```

---

## 🤖 Instrucciones para Claude Code - Tests de Slice

### Al generar tests de slice, SIEMPRE:

1. **Elegir el slice apropiado:**
    - Controllers → @WebMvcTest (capa web)
    - Repositories → @DataJpaTest (capa de datos)
    - JSON → @JsonTest (serialización)
    - Services → NO usar slice, usar unit tests

2. **Para @WebMvcTest:**
    - Cargar solo el controller específico
    - Mockear todos los @Service con @MockBean
    - Testear HTTP requests/responses
    - Verificar JSON y status codes

3. **Para @DataJpaTest:**
    - Cargar solo JPA + DB de test
    - Usar TestEntityManager para datos
    - NO mockear el repository bajo test
    - Verificar queries contra DB real

4. **Estructura de tests:**
    - Un test class por controller/repository
    - Tests enfocados en la capa específica
    - Más rápidos que integración completa

5. **Imports para slice tests:**
```java
// WebMvcTest
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

// DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

// JUnit 5
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

// MockMvc
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
```

6. **Verificaciones por tipo:**
    - @WebMvcTest: status, JSON, headers, llamadas a service
    - @DataJpaTest: resultados de queries, estado en DB

---

## 🚫 Errores Comunes en Tests de Slice

### ❌ NO HACER:

```java
// ❌ NO usar @SpringBootTest para slices
@SpringBootTest
class UserControllerTest { } // Usa @WebMvcTest

// ❌ NO mockear repositories en @DataJpaTest
@MockBean
private UserRepository repository; // Debe ser real

// ❌ NO testear lógica de negocio en controllers
@Test
void shouldCalculateTotal() { } // Pertenece a unit tests

// ❌ NO tests sin verificar response
mockMvc.perform(get("/users")).andReturn(); // Falta andExpect()

// ❌ NO usar @Autowired en @WebMvcTest
@Autowired
private UserService service; // Usa @MockBean

// ❌ NO hardcodear URLs sin explicar
mockMvc.perform(get("/api/v1/users/123")); // ¿Por qué 123?
```

### ✅ HACER:

```java
// ✅ Usar slice apropiado
@WebMvcTest(UserController.class)
class UserControllerTest {
    @MockBean
    private UserService service; // Mockear dependencias
}

// ✅ Verificar HTTP responses completamente
@Test
void shouldReturnUser() throws Exception {
    mockMvc.perform(get("/users/{id}", userId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("John"));
}

// ✅ Usar TestEntityManager en @DataJpaTest
@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    
    @Autowired
    private UserRepository repository;
}

// ✅ Nombres que indican qué se testea
@Test
@DisplayName("GET /users/{id} retorna 200 con usuario existente")
void shouldReturn200_WhenUserExists() { }

// ✅ Usar constantes para datos de test
private static final String TEST_USER_NAME = "John Doe";
```

---

## 📊 Checklist Final para Tests de Slice

Antes de considerar completo un test de slice, verificar:

```
□ Usa anotación correcta (@WebMvcTest o @DataJpaTest)
□ Carga solo la capa necesaria (no contexto completo)
□ Para @WebMvcTest: mockea services, verifica HTTP
□ Para @DataJpaTest: usa DB real, verifica queries
□ Tests son independientes y rápidos (< 5 segundos)
□ Nombres indican endpoint/query específico
□ Incluye casos de error apropiados
□ Código compila y tests pasan
□ NO duplica lógica de unit tests
```

---

## 🎯 Ejemplos de Prompt para Claude Code

### Para generar @WebMvcTest:

```
Genera tests de slice para UserController usando @WebMvcTest
siguiendo SLICE_TESTING_GUIDE.md. Incluye tests para todos los
endpoints HTTP con validación de status codes, JSON responses
y manejo de errores.
```

### Para generar @DataJpaTest:

```
Crea tests de slice para ProductRepository usando @DataJpaTest
según SLICE_TESTING_GUIDE.md. Testea operaciones CRUD y todas
las queries personalizadas con datos reales en DB.
```

### Para agregar tests faltantes a slice:

```
Analiza UserControllerTest existente y agrega los tests de slice
faltantes para endpoints no cubiertos, siguiendo el template
de @WebMvcTest en SLICE_TESTING_GUIDE.md.
```

---

## 📚 Recursos Adicionales

- **Ejecutar slice tests:** `mvn test -Dtest="*ControllerTest"` o `mvn test -Dtest="*RepositoryTest"`
- **Ejecutar solo web slice:** `mvn test -Dgroups=web-slice` (si se configura)
- **Debug slice test:** `mvn test -Dtest=NombreTest -DforkCount=0`
- **Documentación Spring Boot Test:** @WebMvcTest, @DataJpaTest

---

## 🔍 Validación de Tests de Slice

Después de generar tests de slice, ejecutar:

```bash
# Ejecutar solo slice tests
mvn test -Dtest="*Test" -Dspring.profiles.active=test

# Verificar que no cargan contexto completo
mvn test -Dtest=NombreTest -Dlogging.level.org.springframework=DEBUG

# Medir tiempo de carga de contexto
mvn test -Dtest=NombreTest -Dmaven.surefire.debug=true
```

---

**Última actualización:** 2025-10-08
**Versión:** 1.0.0

---

**NOTA IMPORTANTE:** Los slice tests complementan los unit tests. Usa @WebMvcTest para controllers y @DataJpaTest para repositories. Para lógica de negocio, usa unit tests. Para flujos end-to-end, usa integration tests.