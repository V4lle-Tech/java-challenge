# Integración con Servicios de Terceros - Spring Boot 3.x

## Propósito
Guía para integrar servicios de terceros en Spring Boot, alineada con estándares de AGENTS.md, enfocándose en configuración segura y eficiente.

## Principios
- Configurar servicios en `application.yml` usando perfiles.
- Usar `@Bean` para inyección de dependencias.
- Nunca hardcodear secretos; usar variables de entorno (AGENTS.md).
- Preferir operaciones de archivo único para optimizar tokens.

## ✅ HACER
- **APIs REST con RestTemplate**:
  - Configurar en clase `@Configuration`:
    ```java
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    ```
  - Inject via constructor:
    ```java
    private final RestTemplate restTemplate;
    ```
- **OAuth con Spring Security**:
  - Agregar dependencia: `spring-boot-starter-oauth2-client`.
  - Configurar en `application.yml`:
    ```yaml
    spring:
      security:
        oauth2:
          client:
            registration:
              custom:
                client-id: ${CLIENT_ID}
                client-secret: ${CLIENT_SECRET}
                authorization-grant-type: client_credentials
                token-uri: https://api.thirdparty.com/token
    ```
  - Usar `@EnableOAuth2Client` e inyectar `OAuth2RestTemplate`.
- Almacenar secretos en variables de entorno (ej: `CLIENT_ID`, `CLIENT_SECRET`).
- Usar `@Value` o `@ConfigurationProperties` para configuración dinámica.

## ❌ NO HACER
- No hardcodear claves API o secretos en código (AGENTS.md).
- No usar `@Autowired` para inyección; usar inyección por constructor.
- No modificar `pom.xml` sin permiso.
- Evitar clases genéricas `Utils` para llamadas de servicio.

## Mejores Prácticas
- Usar `RestClient` (Spring 6.1+) para llamadas REST modernas.
- Implementar mecanismos de reintento con `@Retryable` para APIs inestables.
- Registrar requests/responses con SLF4J (AGENTS.md).
- Validar configuraciones: `mvn spring-boot:run -Pdev`.

## Ejemplo de Uso
```java
@Service
@RequiredArgsConstructor
public class ThirdPartyService {
    private final RestTemplate restTemplate;

    public String fetchData() {
        return restTemplate.getForObject("https://api.thirdparty.com/data", String.class);
    }
}
```

## Validación
- Probar conectividad: `curl -v ${API_URL}`.
- Verificar configuraciones: `mvn test -Dtest=ThirdPartyServiceTest`.
- Verificar logs: `grep "third-party" logs/app.log`.

**Última Actualización**: 2025-10-08
**Versión**: 1.0.0