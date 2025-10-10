ARG JAVA_VERSION="21"
ARG USE_MAVEN="true"
ARG USE_GRADLE="false"

FROM mcr.microsoft.com/devcontainers/java:${JAVA_VERSION}

# Evitar prompts durante la instalación
ENV DEBIAN_FRONTEND=noninteractive
ENV TZ=UTC

# Instalar dependencias base adicionales
RUN apt-get update && apt-get install -y \
    curl \
    git \
    unzip \
    zip \
    sudo \
    ca-certificates \
    gnupg \
    lsb-release \
    software-properties-common \
    && rm -rf /var/lib/apt/lists/*

# Instalar Maven si está habilitado
ARG USE_MAVEN
RUN if [ "$USE_MAVEN" = "true" ]; then \
    apt-get update && apt-get install -y maven \
    && rm -rf /var/lib/apt/lists/* \
    && echo "✅ Maven instalado"; \
    fi

# Instalar Gradle si está habilitado
ARG USE_GRADLE
RUN if [ "$USE_GRADLE" = "true" ]; then \
    apt-get update && apt-get install -y gradle \
    && rm -rf /var/lib/apt/lists/* \
    && echo "✅ Gradle instalado"; \
    fi

# Instalar herramientas adicionales
RUN apt-get update && apt-get install -y \
    htop \
    vim \
    nano \
    jq \
    && rm -rf /var/lib/apt/lists/*

# Crear usuario coder
RUN useradd -m -s /bin/bash coder && \
    echo "coder ALL=(ALL) NOPASSWD:ALL" >> /etc/sudoers.d/coder && \
    chmod 0440 /etc/sudoers.d/coder

# Configurar directorio de trabajo
WORKDIR /workspaces
RUN chown -R coder:coder /workspaces

# Cambiar al usuario coder
USER coder

# Configurar Maven si está instalado
ARG USE_MAVEN
RUN if [ "$USE_MAVEN" = "true" ]; then \
    mkdir -p /home/coder/.m2; \
    fi

# Copiar settings.xml solo si Maven está habilitado
COPY --chown=coder:coder settings.xml /tmp/settings.xml
RUN if [ "$USE_MAVEN" = "true" ]; then \
    mv /tmp/settings.xml /home/coder/.m2/settings.xml; \
    else \
    rm /tmp/settings.xml; \
    fi

# Configuración de Git
RUN git config --global init.defaultBranch main

# Exponer puertos comunes para Spring Boot
EXPOSE 8080 8081 8082 5005

# Comando por defecto
CMD ["/bin/bash"]