# Proyecto Base Java Spring Boot

Este es un proyecto base mínimo para desarrollo con Java 21 en Coder.

## Estructura del Proyecto

```
.
├── .devcontainer/              # Configuración del devcontainer
│   ├── Dockerfile
│   ├── devcontainer.json
│   ├── post-create.sh
│   ├── startup.sh
│   └── settings.xml
├── src/
│   └── main/
│       └── java/
│           └── com/example/demo/
│               └── Application.java
├── pom.xml                     # Configuración de Maven
├── .gitignore
├── .editorconfig
└── README.md                   # Este archivo
```

## Inicio Rápido

### 1. Compilar y ejecutar

```bash
# Compilar el proyecto
mvn clean compile

# Ejecutar la aplicación
mvn exec:java -Dexec.mainClass="com.example.demo.Application"

# O usar el alias
mcc    # mvn clean compile
```

### 2. Crear un nuevo proyecto Spring Boot

Usa la función helper incluida:

```bash
# Crear un nuevo proyecto Spring Boot completo
create-spring-boot mi-proyecto

# Crear un proyecto Maven básico
create-spring-maven mi-proyecto com.miempresa
```

### 3. Comandos útiles

```bash
# Compilar
mvn compile

# Empaquetar en JAR
mvn package

# Ejecutar tests (cuando los agregues)
mvn test

# Limpiar build artifacts
mvn clean
```

## Estructura Recomendada para Spring Boot

```
.
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/demo/
│   │   │       ├── DemoApplication.java
│   │   │       ├── controller/
│   │   │       │   └── HelloController.java
│   │   │       ├── service/
│   │   │       │   └── HelloService.java
│   │   │       ├── model/
│   │   │       │   └── User.java
│   │   │       └── repository/
│   │   │           └── UserRepository.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── static/
│   │       └── templates/
│   └── test/
│       └── java/
│           └── com/example/demo/
│               └── DemoApplicationTests.java
├── pom.xml
└── README.md
```

## Migrar a Spring Boot

Para convertir este proyecto en un proyecto Spring Boot:

1. Actualiza el `pom.xml` para usar Spring Boot:

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.2.0</version>
</parent>

<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
</dependencies>
```

2. Crea una clase principal con `@SpringBootApplication`:

```java
@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
```

3. Ejecuta: `mvn spring-boot:run`

## Aliases Configurados

El workspace incluye estos aliases útiles:

**Maven:**
- `m` → `mvn`
- `mci` → `mvn clean install`
- `mcc` → `mvn clean compile`
- `mt` → `mvn test`
- `mrun` → `mvn spring-boot:run`
- `mdeps` → `mvn dependency:tree`
- `mpkg` → `mvn package`

**Gradle:**
- `g` → `gradle`
- `gb` → `gradle build`
- `grun` → `gradle bootRun`
- `gt` → `gradle test`

**Funciones:**
- `create-spring-boot <nombre>` - Crea un proyecto Spring Boot completo
- `create-spring-maven <nombre>` - Crea un proyecto Maven básico
- `run-tests` - Ejecuta tests (Maven o Gradle automáticamente)

## Agregar Dependencias

Para agregar Spring Boot Web, por ejemplo:

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
        <version>3.2.0</version>
    </dependency>
</dependencies>
```

Luego ejecuta: `mvn clean install`

## Siguientes Pasos

1. **Reemplazar este proyecto base** con tu código existente, o
2. **Crear un nuevo proyecto** usando `create-spring-boot`, o
3. **Migrar a Spring Boot** siguiendo las instrucciones arriba
4. **Agregar tests** usando JUnit 5
5. **Configurar una base de datos** con Spring Data JPA

## Recursos

- [Documentación de Maven](https://maven.apache.org/guides/)
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Initializr](https://start.spring.io/)
- [Java SE Documentation](https://docs.oracle.com/en/java/javase/21/)
