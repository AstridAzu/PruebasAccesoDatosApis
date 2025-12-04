# API de Blog (Posts y Comentarios)

## Descripción
API REST para un sistema de blog con gestión de posts y comentarios. Relación uno-a-muchos entre Posts y Comentarios.

## Arquitectura
**Patrón**: Model-Service-Controller (MSC)

### Estructura del Proyecto
```
Ejercicio1Opcional/
├── controller/              # Controladores HTTP
│   ├── PostController.java
│   └── ComentarioController.java
├── services/               # Lógica de negocio
│   ├── PostService.java
│   └── ComentarioService.java
├── models/                 # Modelos de datos
│   ├── Post.java
│   └── Comentario.java
├── dto/                    # Data Transfer Objects
│   ├── PostDTO.java
│   └── ComentarioDTO.java
├── exceptions/             # Excepciones personalizadas
│   ├── PostNoEncontradoException.java
│   ├── ComentarioNoEncontradoException.java
│   ├── DatosInvalidosException.java
│   └── ErrorResponse.java
└── MainBlog.java          # Punto de entrada
```

## Componentes

### Models
- **Post**: Entidad que representa un post del blog
  - `id`: Identificador único
  - `titulo`: Título del post
  - `contenido`: Contenido del post
  - `autor`: Nombre del autor
  - `fechaPublicacion`: Fecha y hora de publicación
  - `comentarios`: Lista de comentarios asociados

- **Comentario**: Entidad que representa un comentario
  - `id`: Identificador único
  - `autor`: Nombre del autor del comentario
  - `contenido`: Contenido del comentario
  - `fecha`: Fecha y hora del comentario

### DTOs
- **PostDTO**: Objeto para crear/actualizar posts
- **ComentarioDTO**: Objeto para crear comentarios

### Services
- **PostService**: Gestión de posts
  - CRUD de posts
  - Validaciones de título y contenido
  - Almacenamiento en memoria

- **ComentarioService**: Gestión de comentarios
  - Creación y eliminación de comentarios
  - Validaciones de contenido
  - Asociación con posts

### Controllers
- **PostController**: Endpoints de posts
- **ComentarioController**: Endpoints de comentarios

### Exceptions
- **PostNoEncontradoException**: Post no existe (404)
- **ComentarioNoEncontradoException**: Comentario no existe (404)
- **DatosInvalidosException**: Datos inválidos (400)
- **ErrorResponse**: Formato estandarizado de errores

## Endpoints

### Posts
| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/posts` | Obtener todos los posts |
| GET | `/posts/{id}` | Obtener un post específico |
| POST | `/posts` | Crear nuevo post |
| PUT | `/posts/{id}` | Actualizar post existente |
| DELETE | `/posts/{id}` | Eliminar post |

### Comentarios
| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/posts/{id}/comentarios` | Obtener comentarios de un post |
| POST | `/posts/{id}/comentarios` | Añadir comentario a un post |
| DELETE | `/posts/{postId}/comentarios/{comentarioId}` | Eliminar comentario |

## Configuración
- **Puerto**: 7070
- **Framework**: Javalin
- **CORS**: Habilitado para todos los orígenes
- **Logs**: Dev logging habilitado

## Validaciones

### Posts
- Título obligatorio (máx. 200 caracteres)
- Contenido obligatorio
- Autor obligatorio

### Comentarios
- Autor obligatorio
- Contenido obligatorio (máx. 500 caracteres)

## Relaciones
- **Post → Comentarios**: Uno a muchos
- Cada post puede tener múltiples comentarios
- Los comentarios están asociados a un post específico
- Al eliminar un post, se eliminan sus comentarios

## Manejo de Errores
- 400: Datos inválidos
- 404: Post o comentario no encontrado
- 500: Error interno del servidor

## Almacenamiento
- **Tipo**: En memoria (ConcurrentHashMap)
- **Thread-safe**: Sí
- Los datos se pierden al reiniciar la aplicación

## Ejemplo de Uso

### Crear un post
```bash
POST http://localhost:7070/posts
Content-Type: application/json

{
  "titulo": "Introducción a Javalin",
  "contenido": "Javalin es un framework web ligero...",
  "autor": "María González"
}
```

### Añadir un comentario a un post
```bash
POST http://localhost:7070/posts/1/comentarios
Content-Type: application/json

{
  "autor": "Juan Pérez",
  "contenido": "Excelente artículo, muy útil!"
}
```

### Obtener un post con sus comentarios
```bash
GET http://localhost:7070/posts/1
```

## Datos de Ejemplo
La aplicación se inicializa con 2 posts de ejemplo:
1. "Introducción a Javalin"
2. "APIs REST con Java"

## Ejecutar
```java
// Ejecutar desde: MainBlog.java
public static void main(String[] args) {
    // La aplicación inicia en http://localhost:7070
}
```
