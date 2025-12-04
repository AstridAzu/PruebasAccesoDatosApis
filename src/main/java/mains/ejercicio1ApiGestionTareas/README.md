# API de Gestión de Tareas

## Descripción
API REST para gestionar tareas con operaciones CRUD completas.

## Arquitectura
**Patrón**: Model-Service-Controller (MSC)

### Estructura del Proyecto
```
ejercicio1ApiGestionTareas/
├── controller/          # Controladores HTTP
│   └── TareaController.java
├── services/           # Lógica de negocio
│   └── TareaService.java
├── models/             # Modelos de datos
│   └── Tarea.java
├── dto/                # Data Transfer Objects
│   └── TareaDTO.java
├── exceptions/         # Excepciones personalizadas
│   ├── TareaNoEncontradaException.java
│   ├── DatosInvalidosException.java
│   └── ErrorResponse.java
└── mainAPIGestiónTareasEjercicio1.java  # Punto de entrada
```

## Componentes

### Models
- **Tarea**: Entidad que representa una tarea
  - `id`: Identificador único
  - `titulo`: Título de la tarea
  - `descripcion`: Descripción detallada
  - `completada`: Estado de la tarea

### DTOs
- **TareaDTO**: Objeto para transferencia de datos en peticiones
  - Usado en POST y PUT

### Services
- **TareaService**: Contiene la lógica de negocio
  - Validaciones de datos
  - Gestión del almacenamiento en memoria (ConcurrentHashMap)
  - Operaciones CRUD

### Controllers
- **TareaController**: Maneja las peticiones HTTP
  - Transforma requests a DTOs
  - Llama a los servicios
  - Devuelve respuestas HTTP apropiadas

### Exceptions
- **TareaNoEncontradaException**: Lanzada cuando una tarea no existe (404)
- **DatosInvalidosException**: Lanzada cuando los datos son inválidos (400)
- **ErrorResponse**: Formato estandarizado de errores

## Endpoints

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/tareas` | Obtener todas las tareas |
| GET | `/tareas/{id}` | Obtener una tarea específica |
| POST | `/tareas` | Crear nueva tarea |
| PUT | `/tareas/{id}` | Actualizar tarea existente |
| DELETE | `/tareas/{id}` | Eliminar tarea |
| PATCH | `/tareas/{id}/completar` | Marcar tarea como completada |

## Configuración
- **Puerto**: 7070
- **Framework**: Javalin
- **CORS**: Habilitado para todos los orígenes
- **Logs**: Dev logging habilitado

## Validaciones
- Título obligatorio y máximo 100 caracteres
- Las tareas nuevas se crean con `completada = false`

## Manejo de Errores
- 400: Datos inválidos
- 404: Tarea no encontrada
- 500: Error interno del servidor

## Almacenamiento
- **Tipo**: En memoria (ConcurrentHashMap)
- **Thread-safe**: Sí
- Los datos se pierden al reiniciar la aplicación

## Ejemplo de Uso

### Crear una tarea
```bash
POST http://localhost:7070/tareas
Content-Type: application/json

{
  "titulo": "Aprender Javalin",
  "descripcion": "Completar tutorial de API REST"
}
```

### Obtener todas las tareas
```bash
GET http://localhost:7070/tareas
```

### Actualizar una tarea
```bash
PUT http://localhost:7070/tareas/1
Content-Type: application/json

{
  "titulo": "Aprender Javalin (Actualizado)",
  "descripcion": "Tutorial completo"
}
```

## Ejecutar
```java
// Ejecutar desde: mainAPIGestiónTareasEjercicio1.java
public static void main(String[] args) {
    // La aplicación inicia en http://localhost:7070
}
```
