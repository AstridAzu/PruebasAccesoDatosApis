# API de Autenticación Básica

## Descripción
API REST con sistema de autenticación básica mediante tokens. Incluye registro, login y endpoints protegidos.

## Arquitectura
**Patrón**: Model-Service-Repository-Controller con Middleware (MSRCM)

### Estructura del Proyecto
```
jercicio3APIAutenticaciónBásica/
├── controller/              # Controladores HTTP
│   └── AuthController.java
├── services/               # Lógica de negocio
│   └── AuthService.java
├── repositories/           # Acceso a datos
│   ├── UsuarioRepository.java
│   └── TokenRepository.java
├── models/                 # Modelos de datos
│   └── Usuario.java
├── dto/                    # Data Transfer Objects
│   ├── LoginRequest.java
│   ├── RegistroRequest.java
│   └── AuthResponse.java
├── middleware/             # Middlewares de autenticación
│   └── AuthMiddleware.java
└── AuthAPI.java           # Punto de entrada
```

## Componentes

### Models
- **Usuario**: Entidad que representa un usuario del sistema
  - `username`: Nombre de usuario único
  - `password`: Contraseña (en producción debe estar hasheada)
  - `email`: Correo electrónico
  - `fechaRegistro`: Fecha de registro automática

### DTOs
- **RegistroRequest**: Datos para registrar un nuevo usuario
  - `username`: Nombre de usuario
  - `password`: Contraseña
  - `email`: Correo electrónico

- **LoginRequest**: Credenciales para iniciar sesión
  - `username`: Nombre de usuario
  - `password`: Contraseña

- **AuthResponse**: Respuesta tras autenticación exitosa
  - `token`: Token de autenticación generado
  - `username`: Nombre de usuario autenticado

### Repositories
- **UsuarioRepository**: Gestión de usuarios
  - `save()`: Guarda un nuevo usuario
  - `findByUsername()`: Busca usuario por nombre
  - `existsByUsername()`: Verifica si existe el username
  - `findAll()`: Obtiene todos los usuarios

- **TokenRepository**: Gestión de tokens de sesión
  - `save()`: Almacena un token con su usuario asociado
  - `findUsernameByToken()`: Obtiene el username desde un token
  - `delete()`: Elimina un token (logout)
  - `exists()`: Verifica si un token existe

### Services
- **AuthService**: Lógica de autenticación y autorización
  - `registrarUsuario()`: Registra un nuevo usuario
  - `login()`: Autentica usuario y genera token
  - `validarToken()`: Valida token y retorna usuario
  - `logout()`: Cierra sesión eliminando token
  - `generarToken()`: Genera token simple (username + timestamp)

### Controllers
- **AuthController**: Maneja endpoints de autenticación
  - `registrar()`: Endpoint de registro
  - `login()`: Endpoint de inicio de sesión
  - `obtenerPerfil()`: Endpoint protegido (requiere autenticación)

### Middleware
- **AuthMiddleware**: Middleware de verificación de autenticación
  - `verificarAutenticacion()`: Valida token en header Authorization
  - Intercepta requests antes de llegar a endpoints protegidos
  - Almacena usuario en contexto para uso posterior

## Endpoints

### Públicos (No requieren autenticación)
| Método | Ruta | Descripción |
|--------|------|-------------|
| POST | `/auth/registrar` | Registrar nuevo usuario |
| POST | `/auth/login` | Iniciar sesión |

### Protegidos (Requieren token)
| Método | Ruta | Descripción | Header Requerido |
|--------|------|-------------|------------------|
| GET | `/perfil` | Obtener perfil del usuario | Authorization: {token} |

## Configuración
- **Puerto**: 7070
- **Framework**: Javalin
- **Content-Type**: application/json
- **Sistema de Tokens**: Simple (username + timestamp)

## Validaciones

### Registro
- Username obligatorio (no vacío)
- Password mínimo 6 caracteres
- Email válido (debe contener @)
- Username único (no puede existir otro igual)

### Login
- Username y password obligatorios
- Credenciales deben coincidir con usuario registrado

## Flujo de Autenticación

### 1. Registro
```
Cliente → POST /auth/registrar → Validaciones → Crear Usuario → Guardar en Repository
```

### 2. Login
```
Cliente → POST /auth/login → Validar Credenciales → Generar Token → Guardar Token → Retornar Token
```

### 3. Acceso a Recurso Protegido
```
Cliente → GET /perfil (con header Authorization) → Middleware valida token → 
Controller obtiene usuario del contexto → Retorna información del perfil
```

## Sistema de Tokens

### Generación
- **Formato**: `{username}_{timestamp}`
- **Ejemplo**: `juan_1733356800000`
- **Almacenamiento**: En memoria (HashMap)

### Uso
- Se envía en el header `Authorization` de cada petición
- El middleware lo valida antes de acceder a endpoints protegidos
- Si es inválido o no existe, retorna 401 Unauthorized

## Manejo de Errores

### Registro
- 201: Usuario registrado exitosamente
- 400: Datos inválidos o username ya existe

### Login
- 200: Login exitoso, token generado
- 401: Credenciales inválidas
- 400: Datos inválidos

### Endpoints Protegidos
- 200: Acceso exitoso
- 401: No autorizado (token faltante, inválido o expirado)

## Seguridad

### ⚠️ IMPORTANTE - Solo para Desarrollo
Esta implementación es **básica y educativa**. Para producción:

1. **Passwords**: 
   - ❌ Actual: Texto plano
   - ✅ Producción: Usar BCrypt para hashear
   ```java
   // Registrar: BCrypt.hashpw(password, BCrypt.gensalt())
   // Login: BCrypt.checkpw(password, hashedPassword)
   ```

2. **Tokens**:
   - ❌ Actual: Simple string (username + timestamp)
   - ✅ Producción: Usar JWT (JSON Web Tokens)

3. **HTTPS**:
   - ❌ Actual: HTTP
   - ✅ Producción: Usar HTTPS obligatoriamente

4. **Token Expiration**:
   - ❌ Actual: No expiran
   - ✅ Producción: Implementar expiración de tokens

## Ejemplo de Uso

### 1. Registrar un usuario
```bash
POST http://localhost:7070/auth/registrar
Content-Type: application/json

{
  "username": "juan",
  "password": "123456",
  "email": "juan@example.com"
}
```

**Respuesta:**
```json
{
  "mensaje": "Usuario registrado exitosamente",
  "username": "juan"
}
```

### 2. Iniciar sesión
```bash
POST http://localhost:7070/auth/login
Content-Type: application/json

{
  "username": "juan",
  "password": "123456"
}
```

**Respuesta:**
```json
{
  "token": "juan_1733356800000",
  "username": "juan"
}
```

### 3. Acceder a perfil (con token)
```bash
GET http://localhost:7070/perfil
Authorization: juan_1733356800000
```

**Respuesta:**
```json
{
  "username": "juan",
  "email": "juan@example.com",
  "fechaRegistro": "2025-12-04T15:30:00"
}
```

### 4. Error sin token
```bash
GET http://localhost:7070/perfil
```

**Respuesta (401):**
```json
{
  "error": "No autorizado. Token requerido"
}
```

## Dependency Injection
```
UsuarioRepository ──┐
                    ├─→ AuthService ──→ AuthController
TokenRepository  ───┘                ↓
                                AuthMiddleware
```

## Almacenamiento
- **Usuarios**: En memoria (HashMap)
- **Tokens**: En memoria (HashMap)
- **Persistencia**: No persistente (se pierde al reiniciar)

## Middleware Pattern

El middleware intercepta peticiones antes de llegar al controlador:

```java
// Configuración en AuthAPI.java
app.before("/perfil", authMiddleware::verificarAutenticacion);
app.get("/perfil", authController::obtenerPerfil);
```

Flujo:
1. Request llega a `/perfil`
2. `before` ejecuta el middleware
3. Middleware valida el token
4. Si es válido, almacena usuario en contexto y continúa
5. Si es inválido, retorna 401 y detiene la petición
6. Controller accede al usuario desde el contexto

## Casos de Uso
1. **Autenticación de usuarios**: Sistema de login básico
2. **Protección de endpoints**: Recursos que requieren autenticación
3. **Gestión de sesiones**: Control de usuarios activos
4. **Base para aplicaciones**: Foundation para sistemas más complejos

## Ejecutar
```java
// Ejecutar desde: AuthAPI.java
public static void main(String[] args) {
    // La aplicación inicia en http://localhost:7070
}
```

## Mejoras Futuras
- [ ] Implementar JWT en lugar de tokens simples
- [ ] Hashear passwords con BCrypt
- [ ] Expiración automática de tokens
- [ ] Refresh tokens
- [ ] Rate limiting para prevenir ataques de fuerza bruta
- [ ] Roles y permisos (RBAC)
- [ ] Persistencia en base de datos
- [ ] Recuperación de contraseña
- [ ] Verificación de email
- [ ] OAuth2 / Social login
