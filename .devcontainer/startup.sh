#!/bin/bash
set -e

echo "🚀 Iniciando ambiente de desarrollo Java Spring Boot..."

# Configurar PATH
export PATH=/home/coder/.local/bin:/usr/local/bin:$PATH

# Mostrar estado del ambiente
echo ""
echo "📦 Herramientas disponibles:"
command -v java >/dev/null 2>&1 && echo "  ✅ Java: $(java --version 2>&1 | head -n 1)" || echo "  ❌ Java no disponible"
command -v javac >/dev/null 2>&1 && echo "  ✅ Java Compiler: $(javac --version 2>&1)" || echo "  ❌ Java Compiler no disponible"
command -v mvn >/dev/null 2>&1 && echo "  ✅ Maven: $(mvn --version 2>&1 | head -n 1)" || echo "  ❌ Maven no disponible"
command -v gradle >/dev/null 2>&1 && echo "  ✅ Gradle: $(gradle --version 2>&1 | grep "Gradle" | head -n 1)" || echo "  ❌ Gradle no disponible"
command -v git >/dev/null 2>&1 && echo "  ✅ Git: $(git --version)" || echo "  ❌ Git no disponible"

# Detectar tipo de proyecto y mostrar comandos útiles
if [ -f "pom.xml" ]; then
    echo ""
    echo "📋 Proyecto Maven detectado"
    echo "💡 Comandos disponibles:"
    echo "  mvn clean install  # Compilar y empaquetar"
    echo "  mvn spring-boot:run # Ejecutar aplicación"
    echo "  mvn test           # Ejecutar tests"

    # Detectar Spring Boot
    if grep -q "spring-boot" "pom.xml" 2>/dev/null; then
        echo ""
        echo "⚡ Proyecto Spring Boot detectado"
        echo "💡 La aplicación estará disponible en:"
        echo "  http://localhost:8080"
        echo "  http://localhost:8080/actuator (si Actuator está habilitado)"
    fi
elif [ -f "build.gradle" ] || [ -f "build.gradle.kts" ]; then
    echo ""
    echo "📋 Proyecto Gradle detectado"
    echo "💡 Comandos disponibles:"
    echo "  ./gradlew build    # Compilar y empaquetar"
    echo "  ./gradlew bootRun  # Ejecutar aplicación"
    echo "  ./gradlew test     # Ejecutar tests"

    # Detectar Spring Boot
    if grep -q "spring-boot" "build.gradle" 2>/dev/null || grep -q "spring-boot" "build.gradle.kts" 2>/dev/null; then
        echo ""
        echo "⚡ Proyecto Spring Boot detectado"
        echo "💡 La aplicación estará disponible en:"
        echo "  http://localhost:8080"
        echo "  http://localhost:8080/actuator (si Actuator está habilitado)"
    fi
fi

echo ""
echo "✨ Ambiente Java listo para usar"
echo "📁 Directorio actual: $(pwd)"