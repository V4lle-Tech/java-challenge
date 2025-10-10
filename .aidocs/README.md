# .aidocs - Documentación para Agentes AI

## Propósito
Esta carpeta contiene todas las guías, estándares e instrucciones para que los agentes AI (Claude Code, GitHub Copilot, Cursor) trabajen efectivamente en este proyecto Spring Boot.

## Estructura

### Archivos Principales
- `AGENTS.md` - Archivo universal que funciona con todos los agentes AI
- Symlinks recomendados desde la raíz del proyecto:
  ```bash
  ln -s .aidocs/AGENTS.md ./AGENTS.md
  ln -s .aidocs/AGENTS.md ./.cursorrules
  ln -s .aidocs/AGENTS.md ./CLAUDE.md
  ```

### Carpetas por Categoría

| Carpeta | Contenido |
|---------|-----------|
| `rules/` | Pautas de desarrollo |
| `templates/` | Plantillas de código reutilizables |

## Uso por Agentes AI

### Claude Code
- Lee automáticamente `CLAUDE.md` desde la raíz
- Comandos personalizados en `.claude/commands/`

### GitHub Copilot
- Busca `.github/copilot-instructions.md`
- Path-specific rules en `.github/instructions/`

### Cursor
- Lee `.cursorrules` desde la raíz
- Rules avanzadas en `.cursor/rules/`

## Principios Clave

1. **Minimalismo**: Instrucciones concisas y directas
2. **Especificidad**: Versiones exactas y comandos precisos
3. **Prevención**: Documentar errores comunes con ejemplos
4. **Optimización**: Comandos single-file para ahorrar tokens
5. **Seguridad**: Permisos explícitos para operaciones críticas

## Actualización

- Agregar reglas inmediatamente al observar errores repetidos
- Revisar mensualmente y eliminar reglas obsoletas
- Mantener sincronizados los symlinks con el archivo principal
- Documentar decisiones arquitectónicas en `project-context/`