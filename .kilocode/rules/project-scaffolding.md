# Estructura del Proyecto - Spring Boot 3.x

## Propósito
Definir estructura y convenciones de nomenclatura para proyectos Spring Boot, alineadas con AGENTS.md.

## Principios
- Usar estructura de paquetes estándar: `com.example.demo`.
- Seguir convenciones claras de nomenclatura para clases.
- No crear nuevos paquetes sin permiso (AGENTS.md).
- Mantener estructura minimal y consistente.

## Estructura de Paquetes
```
src/
└── main/
    └── java/
        └── com.example.demo/
            ├── controller/    # REST endpoints
            ├── service/       # Business logic
            ├── repository/    # Data access
            ├── model/         # JPA entities
            ├── dto/           # Data transfer objects
            ├── exception/     # Custom exceptions
            ├── config/        # Configuration classes
            └── util/          # Utilities (avoid generic)
└── test/                  # Mirrors main structure
```

## Convenciones de Nomenclatura
- **Controllers**: `{Entidad}Controller` (ej: `UserController`).
- **Services**: `{Entidad}Service` (ej: `UserService`).
- **Repositories**: `{Entidad}Repository` (ej: `UserRepository`).
- **Entities**: Sustantivo singular (ej: `User`).
- **DTOs**: `{Entidad}DTO`, `Create{Entidad}Request` (ej: `UserDTO`, `CreateUserRequest`).
- **Exceptions**: `{Entidad}NotFoundException` (ej: `UserNotFoundException`).

## ✅ HACER
- Colocar clase principal en `com.example.demo`: `Application.java`.
- Usar `@RestController`, `@Service`, `@Repository` por capa.
- Verificar estructura existente: `find . -name "*Controller*"` antes de crear archivos.

## ❌ NO HACER
- No crear nuevos paquetes sin aprobación (AGENTS.md).
- No usar nombres genéricos (ej: `Utils`, `Common`).
- No desviarse de la estructura de paquetes.

## Validación
- Verificar estructura: `tree src/main/java/com/example/demo`.
- Compilar: `mvn compile -Djavac.source.files=src/main/java/com/example/demo/**/*.java`.
- Verificar estilo: `mvn checkstyle:check`.

**Última Actualización**: 2025-10-08
**Versión**: 1.0.0