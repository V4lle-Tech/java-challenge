# Unit Testing Guide - Service Layer Tests

Esta guía se enfoca exclusivamente en la generación de tests unitarios para la capa de servicios en Spring Boot usando JUnit 5 y Mockito.

## 📋 Contexto del Proyecto

**Stack Tecnológico:**
- Java 21 (LTS)
- Spring Boot 3.x
- JUnit 5 + Mockito
- AssertJ (opcional para assertions fluidas)

**Estructura del Proyecto:**
```
src/
├── main/
│   ├── java/com/example/demo/
│   │   ├── service/         # Business logic - FOCO DE ESTOS TESTS
│   │   ├── repository/      # Mockeado en tests unitarios
│   │   ├── model/           # JPA entities
│   │   ├── dto/             # Data transfer (use Records)
│   │   └── exception/       # Custom exceptions
└── test/                     # Mirror main structure
```

---

## 🎯 Propósito de los Tests Unitarios

Los tests unitarios verifican la lógica de negocio en aislamiento:
- **NO** cargan el contexto de Spring (@SpringBootTest)
- **MOCKean** todas las dependencias externas (repositories, services externos)
- **AISLAN** la lógica pura del servicio
- **EJECUTAN** rápidamente (milisegundos)

---

## 📝 Template para Test Unitario de Servicio

### TEST UNITARIO DE SERVICIO

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

**Checklist obligatorio para tests unitarios de servicio:**
- [ ] Test del happy path (caso exitoso)
- [ ] Test de cada excepción que puede lanzar el método
- [ ] Test con entrada nula
- [ ] Test con listas/colecciones vacías
- [ ] Test de validaciones de negocio
- [ ] Verificar que se llaman los métodos de dependencias con `verify()`
- [ ] NO cargar Spring Context (solo `@ExtendWith(MockitoExtension.class)`)
- [ ] Usar `@Mock` para dependencias y `@InjectMocks` para el servicio bajo test

---

## 🤖 Instrucciones para Claude Code - Tests Unitarios

### Al generar tests unitarios automáticamente, SIEMPRE:

1. **Verificar que es un Service:**
    - Solo aplicar a clases `@Service`
    - Si no es service, redirigir a la guía apropiada

2. **Analizar dependencias:**
    - Listar todos los campos `@Autowired` o constructor
    - Identificar repositories, otros services, mappers, etc.
    - Todos serán `@Mock`eados

3. **Generar tests para CADA método público:**
    - Happy path (caso exitoso)
    - Casos de error (cada excepción posible)
    - Edge cases (null, vacío, límites)

4. **Estructura de test obligatoria:**
    - `@ExtendWith(MockitoExtension.class)`
    - `@Mock` para cada dependencia
    - `@InjectMocks` para el service bajo test
    - Given-When-Then clara

5. **Imports correctos para unit tests:**
```java
// JUnit 5
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

// Mockito
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

// Assertions
import static org.junit.jupiter.api.Assertions.*;
```

6. **Verificaciones obligatorias:**
    - Usar `verify()` para confirmar llamadas a dependencias
    - Usar `assertNotNull()` para objetos retornados
    - Usar `assertEquals()` con valores específicos
    - Usar `assertThrows()` para excepciones
    - NUNCA usar `@SpringBootTest` o contextos de Spring

---

## 🚫 Errores Comunes en Tests Unitarios

### ❌ NO HACER:

```java
// ❌ NO cargar Spring Context
@SpringBootTest
class UserServiceTest { }

// ❌ NO usar @Autowired en unit tests
@Autowired
private UserService userService;

// ❌ NO testear entidades o getters/setters
@Test
void testUserGetters() {
    User user = new User();
    user.setName("Test");
    assertEquals("Test", user.getName());
}

// ❌ NO tests sin verify() de dependencias
@Test
void testSaveUser() {
    userService.save(user);
    // Falta verify(repository).save(user);
}

// ❌ NO nombres genéricos
@Test
void test1() { }

// ❌ NO hardcodear valores sin explicación
assertEquals(100, result);  // ¿Qué significa 100?
```

### ✅ HACER:

```java
// ✅ Usar MockitoExtension
@ExtendWith(MockitoExtension.class)
class UserServiceTest { }

// ✅ Mockear dependencias
@Mock
private UserRepository repository;
@InjectMocks
private UserService service;

// ✅ Testear lógica de negocio
@Test
@DisplayName("Debe crear usuario válido")
void shouldCreateValidUser() {
    // Given
    when(repository.save(any(User.class))).thenReturn(user);
    
    // When
    User saved = service.create(user);
    
    // Then
    assertNotNull(saved.getId());
    verify(repository).save(user);
}

// ✅ Nombres descriptivos con @DisplayName
@Test
@DisplayName("Debe lanzar excepción cuando email ya existe")
void shouldThrowException_WhenEmailExists() { }

// ✅ Usar constantes para valores mágicos
private static final int MAX_NAME_LENGTH = 100;
assertEquals(MAX_NAME_LENGTH, result);
```

---

## 📊 Checklist Final para Tests Unitarios

Antes de considerar completo un test unitario, verificar:

```
□ Usa @ExtendWith(MockitoExtension.class) - NO @SpringBootTest
□ Todos los métodos públicos tienen tests (happy path + errores)
□ Cada test tiene estructura Given-When-Then clara
□ Nombres descriptivos con @DisplayName
□ @Mock para todas las dependencias inyectadas
□ @InjectMocks para el service bajo test
□ Todos los tests tienen assertions + verify()
□ Tests verifican llamadas a dependencias con verify()
□ No hay tests de getters/setters o entidades
□ No hay valores mágicos sin explicar
□ Tests son independientes y rápidos (< 100ms)
□ Código compila sin errores
□ Tests pasan individualmente y en conjunto
```

---

## 🎯 Ejemplos de Prompt para Claude Code

### Para generar tests unitarios de un Service:

```
Genera tests unitarios completos para UserService siguiendo UNIT_TESTING_GUIDE.md.
Incluye tests para todos los métodos públicos con casos de éxito,
excepciones y edge cases. Usa @ExtendWith(MockitoExtension.class)
y mockea todas las dependencias.
```

### Para agregar tests faltantes a un service:

```
Analiza UserServiceTest existente y genera los tests unitarios que faltan
para cubrir todos los métodos públicos con happy paths, errores y edge cases.
Sigue estrictamente UNIT_TESTING_GUIDE.md.
```

### Para refactorizar tests existentes:

```
Refactoriza UserServiceTest para seguir UNIT_TESTING_GUIDE.md:
- Usa @ExtendWith(MockitoExtension.class)
- Agrega @DisplayName descriptivos
- Incluye verify() para todas las dependencias
- Asegura estructura Given-When-Then clara
```

---

## 📚 Recursos Adicionales

- **Ejecutar tests unitarios:** `mvn test -Dtest=*Test`
- **Ejecutar solo unit tests:** `mvn test -Dgroups=unit` (si se configura)
- **Debug tests:** `mvn test -Dtest=NombreTest -DforkCount=0 -DreuseForks=false`
- **Cobertura unitaria:** `mvn jacoco:report` (filtrar por paquetes)

---

## 🔍 Validación de Tests Unitarios

Después de generar tests unitarios, ejecutar:

```bash
# Ejecutar solo tests unitarios (sin integración)
mvn test -Dtest="*Test" -Dspring.profiles.active=test

# Verificar que no usan Spring Context
mvn test -Dtest=NombreTest -Dspring.test.constructor.autowire.mode=off

# Medir tiempo de ejecución
mvn test -Dtest=NombreTest -Dmaven.surefire.debug=true
```

---

**Última actualización:** 2025-10-08
**Versión:** 1.0.0

---

**NOTA IMPORTANTE:** Esta guía es específica para tests unitarios de servicios. Para otros tipos de tests, consulta las guías especializadas (integration-test.md, integration-slice-testing.md).