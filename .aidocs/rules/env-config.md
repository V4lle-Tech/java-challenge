# Configuración de Entorno - Spring Boot 3.x

## Propósito
Guía para gestionar secretos y variables de entorno en Spring Boot, alineada con AGENTS.md.

## Principios
- Usar `@Value` o `@ConfigurationProperties` para configuración.
- Soporte para perfiles (dev/prod) vía `application-{profile}.yml`.
- Almacenar secretos en `.env`, no en git (AGENTS.md).
- Nunca acceder a configuraciones de producción sin permiso.

## ✅ HACER
- Usar `@Value` para propiedades simples:
  ```java
  @Value("${api.key}")
  private String apiKey;
  ```
- Usar `@ConfigurationProperties` para configuraciones estructuradas:
  ```java
  @ConfigurationProperties(prefix = "app")
  public record AppConfig(String url, String secret) {}
  ```
- Definir perfiles en `application.yml`:
  ```yaml
  spring:
    profiles:
      active: ${SPRING_PROFILES_ACTIVE:dev}
  ---
  spring:
    config:
      activate:
        on-profile: dev
  database:
    url: jdbc:h2:mem:testdb
  ---
  spring:
    config:
      activate:
        on-profile: prod
  database:
    url: ${DATABASE_URL}
  ```

## ❌ NO HACER
- No hardcodear secretos en código o `application.yml`.
- No commitear `.env` a git.
- No modificar configuraciones de producción sin aprobación (AGENTS.md).

## Mejores Prácticas
- Almacenar secretos en `.env`: `export API_KEY=secret`.
- Usar `SPRING_PROFILES_ACTIVE` para cambiar perfiles.
- Validar configuraciones: `mvn spring-boot:run -Pdev`.

## Validación
- Verificar activación de perfil: `grep "Active profiles" logs/app.log`.
- Verificar dependencias: `mvn dependency:tree`.

**Última Actualización**: 2025-10-08
**Versión**: 1.0.0