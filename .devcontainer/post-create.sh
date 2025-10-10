#!/bin/bash
set -e

echo "🔧 Ejecutando configuración post-creación para Java Spring Boot..."

# Configurar Git si no está configurado
if [ -z "$(git config --global user.email)" ]; then
    echo "📝 Configurando Git..."
    git config --global user.email "${GIT_AUTHOR_EMAIL:-coder@example.com}"
    git config --global user.name "${GIT_AUTHOR_NAME:-Coder}"
    git config --global init.defaultBranch main
fi

# Verificar si es un proyecto existente
if [ -f "pom.xml" ]; then
    echo "📦 Proyecto Maven detectado, ejecutando dependency:resolve..."
    mvn dependency:resolve 2>/dev/null || true
elif [ -f "build.gradle" ] || [ -f "build.gradle.kts" ]; then
    echo "📦 Proyecto Gradle detectado, descargando dependencias..."
    gradle dependencies 2>/dev/null || true
else
    echo "ℹ️  No se detectó proyecto Java existente (este es el proyecto base de ejemplo)"
fi

# Configurar alias en .bashrc (al final para que estén disponibles en shells nuevos)
if [ -f "$HOME/.bashrc" ]; then
    SHELL_RC="$HOME/.bashrc"
else
    SHELL_RC="$HOME/.profile"
fi

# Verificar si los alias ya existen para evitar duplicados
if ! grep -q "# Maven aliases" "$SHELL_RC" 2>/dev/null; then
    echo "📝 Configurando aliases y funciones en $SHELL_RC..."
    cat >> "$SHELL_RC" << 'EOF'

# Maven aliases
alias m='mvn'
alias mci='mvn clean install'
alias mcc='mvn clean compile'
alias mt='mvn test'
alias mrun='mvn spring-boot:run'
alias mdebug='mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005"'
alias mdeps='mvn dependency:tree'
alias mpkg='mvn package'

# Gradle aliases
alias g='gradle'
alias gb='gradle build'
alias grun='gradle bootRun'
alias gt='gradle test'
alias gc='gradle clean'
alias gdeps='gradle dependencies'

# Git aliases
alias gs='git status'
alias ga='git add'
alias gci='git commit'
alias gp='git push'
alias gl='git log --oneline'

# Función para crear nuevo proyecto Spring Boot con Maven
create-spring-maven() {
    if [ -z "$1" ]; then
        echo "Uso: create-spring-maven <nombre-proyecto> [groupId]"
        return 1
    fi
    GROUP_ID="${2:-com.example}"
    mvn archetype:generate \
        -DgroupId="$GROUP_ID" \
        -DartifactId="$1" \
        -DarchetypeArtifactId=maven-archetype-quickstart \
        -DinteractiveMode=false
    cd "$1"
    echo "✅ Proyecto Maven creado. Ejecuta 'mvn clean install' para compilar"
}

# Función para crear proyecto Spring Boot desde Spring Initializr
create-spring-boot() {
    if [ -z "$1" ]; then
        echo "Uso: create-spring-boot <nombre-proyecto> [groupId]"
        return 1
    fi
    GROUP_ID="${2:-com.example}"
    curl https://start.spring.io/starter.tgz \
        -d dependencies=web,data-jpa,h2 \
        -d groupId="$GROUP_ID" \
        -d artifactId="$1" \
        -d name="$1" \
        -d type=maven-project \
        -d javaVersion=21 \
        -d bootVersion=3.2.0 | tar -xzvf -
    cd "$1"
    echo "✅ Proyecto Spring Boot creado. Ejecuta 'mvn spring-boot:run' para iniciar"
}

# Función para ejecutar tests de un proyecto específico
run-tests() {
    if [ -f "pom.xml" ]; then
        mvn test
    elif [ -f "build.gradle" ] || [ -f "build.gradle.kts" ]; then
        gradle test
    else
        echo "❌ No se encontró pom.xml ni build.gradle"
    fi
}
EOF
else
    echo "ℹ️  Los aliases ya están configurados en $SHELL_RC"
fi

echo "✅ Configuración post-creación completada"
