# Java Spring Boot DevContainer Template

Este repositorio contiene la configuración de devcontainer para workspaces de Java Spring Boot con Maven en Coder.

## Archivos Incluidos

- `Dockerfile` - Imagen base con Java 21 y Maven
- `devcontainer.json` - Configuración VS Code con extensiones Java
- `post-create.sh` - Script de inicialización
- `settings.xml` - Configuración Maven personalizada

## Características

- ✅ **Java 21** - Versión LTS más reciente
- ✅ **Maven** - Gestión de dependencias
- ✅ **Spring Boot** - Framework optimizado
- ✅ **Extensiones VS Code** - Java Pack, Spring Boot Tools
- ✅ **Debugging** - Configuración completa para debugging

## Uso como Submodule

```bash
# Agregar como submodule
git submodule add <url-de-este-repo> .devcontainer
```

## Versionado

Template versionado independientemente para actualizaciones específicas de configuraciones Java/Spring Boot.