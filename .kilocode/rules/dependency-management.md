# Gestión de Dependencias - Spring Boot con Maven

## Propósito
Guía para gestionar dependencias Maven en Spring Boot 3.x, alineada con estándares de AGENTS.md.

## Principios
- Usar Spring Boot BOM para gestionar versiones.
- Agregar dependencias solo con permiso explícito (AGENTS.md).
- Preferir comandos de archivo único para minimizar uso de tokens.

## ✅ HACER
- Usar dependencias `spring-boot-starter` (ej: `spring-boot-starter-web`, `spring-boot-starter-data-jpa`).
- Incluir BOM en `pom.xml`:
  ```xml
  <dependencyManagement>
      <dependencies>
          <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-dependencies</artifactId>
              <version>3.2.0</version>
              <type>pom</type>
              <scope>import</scope>
          </dependency>
      </dependencies>
  </dependencyManagement>
  ```
- Agregar dependencias en `<dependencies>`:
  ```xml
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-web</artifactId>
  </dependency>
  ```

## ❌ NO HACER
- No modificar `pom.xml` sin aprobación (AGENTS.md).
- No hardcodear versiones de dependencias; confiar en BOM.
- No agregar dependencias innecesarias.

## Comandos Clave
- **Listar Dependencias**: `mvn dependency:tree -Dincludes=com.example`
- **Compilar Archivo Único**: `mvn compile -Djavac.source.files=src/main/java/path/to/File.java`
- **Resolver Dependencias**: `mvn dependency:resolve`

## Validación
- Verificar árbol de dependencias: `mvn dependency:tree`.
- Verificar conflictos: `mvn dependency:analyze`.
- Ejecutar `mvn clean install` solo con permiso.

**Última Actualización**: 2025-10-08
**Versión**: 1.0.0