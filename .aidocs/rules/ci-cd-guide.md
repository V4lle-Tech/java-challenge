# Guía CI/CD - Spring Boot con Maven y GitHub Actions

## Propósito
Definir flujos de trabajo CI/CD para Spring Boot usando Maven y GitHub Actions, asegurando compilaciones basadas en pruebas y despliegues seguros, alineados con AGENTS.md.

## Principios del Flujo de Trabajo
- **Compilaciones Basadas en Pruebas**: Ejecutar pruebas antes de cualquier compilación o despliegue.
- **Operaciones de Archivo Único**: Preferir `mvn test -Dtest=ClassTest` para ahorrar recursos.
- **Permisos**: Modificar `.github/workflows/` requiere aprobación explícita (AGENTS.md).
- **Entornos**: Soporte para perfiles dev (H2) y prod (PostgreSQL).

## Flujo de Trabajo de GitHub Actions
- **Disparador**: En push/PR a `main` o `develop`.
- **Pasos**:
  1. Checkout del código.
  2. Configurar Java 21 y Maven.
  3. Ejecutar pruebas: `mvn test`.
  4. Compilar solo si las pruebas pasan: `mvn package -DskipTests=false`.
  5. Desplegar a Heroku/AWS si está en `main` y las pruebas pasan.

**Ejemplo de Flujo de Trabajo**:
```yaml
name: CI/CD Pipeline
on:
  push:
    branches: [main, develop]
jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - name: Set up JDK 21
        uses: actions/setup-java@v4
        with: { java-version: '21' }
      - name: Run Tests
        run: mvn test
      - name: Build
        run: mvn package -DskipTests=false
      - name: Deploy to Heroku
        if: github.ref == 'refs/heads/main'
        run: mvn heroku:deploy
```

## Comandos Clave
- **Probar Archivo Único**: `mvn test -Dtest=ClassTest`
- **Compilar Archivo Único**: `mvn compile -Djavac.source.files=src/main/java/path/to/File.java`
- **Compilación Completa**: `mvn clean install` (requiere permiso).
- **Formatear**: `mvn spotless:apply -DspotlessFiles=src/main/java/path/to/File.java`

## Despliegue
- **Heroku**: Usar `heroku-maven-plugin` para despliegue. Configurar `app.json` y `Procfile`.
- **AWS**: Desplegar a Elastic Beanstalk vía `aws-maven-plugin`. Usar `Dockerrun.aws.json` para contenedores.
- **Secretos**: Almacenar en GitHub Secrets (ej: `HEROKU_API_KEY`, `AWS_ACCESS_KEY`). Nunca hardcodear (AGENTS.md).

## Mejores Prácticas
- Ejecutar pruebas en paralelo: `mvn test -T 4`.
- Usar perfiles: `mvn package -Pprod`.
- Monitorear con Actuator: `/actuator/health`.
- Validar flujos de trabajo con `act --dryrun`.

## Validación
- Verificar cobertura de pruebas: `mvn jacoco:report`.
- Verificar estilo: `mvn checkstyle:check`.

**Última Actualización**: 2025-10-08
**Versión**: 1.0.0