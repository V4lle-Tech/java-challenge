# Common Mistakes - AI Agents

## 🚫 Top Errores a Evitar

1. **Crear documentación después de tareas**
   ❌ Creando IMPLEMENTATION.md...
   ✅ Tarea completada en UserService.java

2. **Sobredocumentar código**
   ❌ // Este método obtiene usuario por ID
   ✅ (Sin comentario obvio)

3. **Builds completos innecesarios**
   ❌ mvn clean install
   ✅ mvn test -Dtest=UserServiceTest

4. **Field injection**
   ❌ @Autowired private UserRepository repo;
   ✅ private final UserRepository repo;
