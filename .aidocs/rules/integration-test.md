# Integration Testing Guide - End-to-End Tests

Esta guía se enfoca exclusivamente en tests de integración end-to-end que verifican el flujo completo de la aplicación usando Spring Boot Test Context.

## 📋 Contexto del Proyecto

**Stack Tecnológico:**
- Java 21 (LTS)
- Spring Boot 3.x
- JUnit 5 + TestContainers (opcional)
- PostgreSQL/H2 para tests de integración
- REST Assured o MockMvc para HTTP

**Estructura del Proyecto:**
```
src/
├── main/
│   ├── java/com/example/demo/
│   │   ├── controller/      # Endpoints HTTP - PRUEBA REAL
│   │   ├── service/         # Business logic - PRUEBA REAL
│   │   ├── repository/      # Data access - PRUEBA REAL
│   │   ├── model/           # JPA entities - PRUEBA REAL
│   │   └── config/          # Configuration - PRUEBA REAL
│   └── resources/
│       ├── application.yml  # Main configuration
│       └── application-test.yml  # Test configuration
└── test/                     # Mirror main structure
    └── integration/          # Tests de integración
```

---

## 🎯 Propósito de los Tests de Integración

Los tests de integración verifican el **FLUJO COMPLETO** end-to-end:
- **CARGAN** el contexto completo de Spring (@SpringBootTest)
- **USAN** base de datos real (H2/PostgreSQL)
- **PRUEBAN** desde HTTP request hasta DB response
- **SON** lentos pero críticos para flujos críticos
- **SE EJECUTAN** en CI/CD, no en desarrollo continuo

---

## 📝 Template para Test de Integración End-to-End

### TEST DE INTEGRACIÓN COMPLETA

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

**Checklist obligatorio para tests de integración:**
- [ ] Usa @SpringBootTest con perfil de test
- [ ] Testea flujos end-to-end completos (HTTP → Service → Repository → DB)
- [ ] Usa base de datos real (H2/PostgreSQL en tests)
- [ ] Verifica estado final en base de datos
- [ ] Incluye @BeforeEach para limpiar datos
- [ ] Testea casos críticos de negocio, no todos los paths
- [ ] NO mockear servicios internos (prueba integración real)

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

## 🤖 Instrucciones para Claude Code - Tests de Integración

### Al generar tests de integración, SIEMPRE:

1. **Solo para flujos críticos:**
    - NO generar para cada método/clase
    - Solo para procesos end-to-end críticos (ej: checkout, user registration)
    - Preguntar antes si es necesario

2. **Usar @SpringBootTest completo:**
    - Cargar todo el contexto de Spring
    - Usar @ActiveProfiles("test") o similar
    - Configurar base de datos de test

3. **Testear desde HTTP hasta DB:**
    - Enviar requests HTTP reales
    - Verificar responses completas
    - Confirmar cambios en base de datos
    - NO mockear servicios internos

4. **Estructura de test:**
    - @SpringBootTest + @AutoConfigureMockMvc
    - @BeforeEach para limpiar DB
    - Tests largos que cubren flujos completos
    - Verificaciones en DB al final

5. **Imports para integración:**
```java
// Spring Boot Test
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;

// JUnit 5
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

// HTTP Testing
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// DB Verification
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.repository.EntityRepository;
```

6. **Verificaciones críticas:**
    - Estado HTTP correcto
    - JSON response válido
    - Datos persistidos en DB
    - Constraints de negocio respetadas

---

## 🚫 Errores Comunes en Tests de Integración

### ❌ NO HACER:

```java
// ❌ NO usar @SpringBootTest para lógica simple
@SpringBootTest
class UserServiceTest { } // Para service, usa unit tests

// ❌ NO mockear servicios en integración
@MockBean
private UserService userService; // Debe ser real

// ❌ NO tests sin verificar DB
@Test
void testCreateUser() {
    // Envía request pero no verifica persistencia
}

// ❌ NO tests que dependen de estado anterior
@Test
void testUpdateUser() {
    // Asume que existe user del test anterior
}

// ❌ NO tests demasiado granulares
@Test
void testValidationAnnotation() { } // Usa unit tests

// ❌ NO hardcodear IDs de test
mockMvc.perform(get("/users/123")); // ¿Por qué 123?
```

### ✅ HACER:

```java
// ✅ Usar @SpringBootTest para flujos completos
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserRegistrationIntegrationTest { }

// ✅ Testear desde HTTP hasta DB
@Test
void shouldCompleteUserRegistration() throws Exception {
    // POST /register
    // Verificar email enviado
    // Verificar usuario en DB
}

// ✅ Limpiar DB entre tests
@BeforeEach
void setUp() {
    userRepository.deleteAll();
}

// ✅ Verificar estado final en DB
@Test
void shouldCreateUser() throws Exception {
    // ... HTTP request ...
    
    // Then - verificar en DB
    List<User> users = userRepository.findAll();
    assertEquals(1, users.size());
}

// ✅ Nombres que indican flujo completo
@Test
@DisplayName("Registro completo: desde signup hasta login")
void shouldCompleteFullRegistrationFlow() { }

// ✅ Usar datos de test consistentes
private static final String TEST_EMAIL = "test@example.com";
```

---

## 📊 Checklist Final para Tests de Integración

Antes de considerar completo un test de integración, verificar:

```
□ Usa @SpringBootTest con @AutoConfigureMockMvc
□ Tiene @ActiveProfiles("test") o configuración de test
□ Incluye @BeforeEach para limpiar base de datos
□ Testea flujo end-to-end completo (HTTP → Business → DB)
□ Verifica estado final en base de datos
□ NO mockea servicios internos (prueba integración real)
□ Usa datos de test consistentes y explicados
□ Tests son independientes (limpian su propio estado)
□ Nombres indican flujos completos, no métodos individuales
□ Código compila y tests pasan en CI/CD
□ Performance aceptable (< 30 segundos por test)
```

---

## 🎯 Ejemplos de Prompt para Claude Code

### Para generar test de integración crítica:

```
Genera test de integración end-to-end para el flujo de checkout
siguiendo INTEGRATION_TEST_GUIDE.md. Incluye creación de orden,
procesamiento de pago, envío de confirmación y verificación en DB.
Usa @SpringBootTest con base de datos real.
```

### Para flujo de registro de usuario:

```
Crea test de integración completo para UserRegistrationIntegrationTest
que cubra: POST /register → validación → email → DB → login.
Sigue el template de integración en INTEGRATION_TEST_GUIDE.md.
```

### Para verificar integración con servicios externos:

```
Genera test de integración para PaymentServiceIntegrationTest que
verifique integración con gateway de pagos. Incluye casos de éxito,
fallos de red y timeouts. Usa @SpringBootTest.
```

---

## 📚 Recursos Adicionales

- **Ejecutar tests de integración:** `mvn verify -Dgroups=integration` (si se configura)
- **Ejecutar solo integración:** `mvn test -Dtest="*IntegrationTest"`
- **Debug integración:** `mvn test -Dtest=NombreIntegrationTest -DforkCount=0`
- **Base de datos de test:** Verificar `application-test.yml`

---

## 🔍 Validación de Tests de Integración

Después de generar tests de integración, ejecutar:

```bash
# Ejecutar solo tests de integración
mvn test -Dtest="*IntegrationTest"

# Verificar con base de datos real
mvn test -Dspring.profiles.active=test

# Medir performance
mvn test -Dtest=NombreIntegrationTest -Dmaven.surefire.debug=true
```

---

**Última actualización:** 2025-10-08
**Versión:** 1.0.0

---

**NOTA IMPORTANTE:** Los tests de integración son lentos y caros. Úsalos solo para flujos críticos end-to-end. Para lógica de negocio, usa tests unitarios. Consulta UNIT_TESTING_GUIDE.md para services.