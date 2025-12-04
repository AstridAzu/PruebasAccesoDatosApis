package mains.Ejercicio1Opcional;

import io.javalin.Javalin;
import mains.Ejercicio1Opcional.controller.ComentarioController;
import mains.Ejercicio1Opcional.controller.PostController;
import mains.Ejercicio1Opcional.exceptions.ComentarioNoEncontradoException;
import mains.Ejercicio1Opcional.exceptions.DatosInvalidosException;
import mains.Ejercicio1Opcional.exceptions.ErrorResponse;
import mains.Ejercicio1Opcional.exceptions.PostNoEncontradoException;
import mains.Ejercicio1Opcional.services.ComentarioService;
import mains.Ejercicio1Opcional.services.PostService;

public class MainBlog {
    public static void main(String[] args) {

        // Crear instancias de servicios
        PostService postService = new PostService();
        ComentarioService comentarioService = new ComentarioService(postService);

        // Crear instancias de controladores
        PostController postController = new PostController(postService);
        ComentarioController comentarioController = new ComentarioController(comentarioService);

        // Configurar Javalin
        Javalin app = Javalin.create(config -> {
            config.plugins.enableCors(cors -> cors.add(it -> it.anyHost()));
            config.plugins.enableDevLogging();
        }).start(7070);

        System.out.println("  API de Blog iniciada en http://localhost:7070");
        System.out.println(" Sistema de Posts y Comentarios");

        // ===== MANEJADORES DE EXCEPCIONES GLOBALES =====

        app.exception(PostNoEncontradoException.class, (e, ctx) -> {
            ctx.status(404);
            ctx.json(new ErrorResponse(e.getMessage(), 404));
        });

        app.exception(ComentarioNoEncontradoException.class, (e, ctx) -> {
            ctx.status(404);
            ctx.json(new ErrorResponse(e.getMessage(), 404));
        });

        app.exception(DatosInvalidosException.class, (e, ctx) -> {
            ctx.status(400);
            ctx.json(new ErrorResponse(e.getMessage(), 400));
        });

        app.exception(Exception.class, (e, ctx) -> {
            ctx.status(500);
            ctx.json(new ErrorResponse("Error interno del servidor", 500));
            e.printStackTrace();
        });

        // ===== RUTAS DE POSTS =====

        // GET /posts - Obtener todos los posts
        app.get("/posts", postController::obtenerTodos);

        // GET /posts/{id} - Obtener un post específico
        app.get("/posts/{id}", postController::obtenerPorId);

        // POST /posts - Crear nuevo post
        app.post("/posts", postController::crear);

        // PUT /posts/{id} - Actualizar post
        app.put("/posts/{id}", postController::actualizar);

        // DELETE /posts/{id} - Eliminar post
        app.delete("/posts/{id}", postController::eliminar);

        // ===== RUTAS DE COMENTARIOS =====

        // GET /posts/{id}/comentarios - Obtener comentarios de un post
        app.get("/posts/{id}/comentarios", comentarioController::obtenerComentarios);

        // POST /posts/{id}/comentarios - Añadir comentario a un post
        app.post("/posts/{id}/comentarios", comentarioController::crearComentario);

        // DELETE /posts/{postId}/comentarios/{comentarioId} - Eliminar comentario
        app.delete("/posts/{postId}/comentarios/{comentarioId}", comentarioController::eliminarComentario);

        imprimirEndpoints();
    }

    private static void imprimirEndpoints() {
        System.out.println(" ENDPOINTS DE POSTS:");
        System.out.println("GET    /posts              - Obtener todos los posts");
        System.out.println("GET    /posts/{id}         - Obtener un post");
        System.out.println("POST   /posts              - Crear nuevo post");
        System.out.println("PUT    /posts/{id}         - Actualizar post");
        System.out.println("DELETE /posts/{id}         - Eliminar post");

        System.out.println("  ENDPOINTS DE COMENTARIOS:");
        System.out.println("GET    /posts/{id}/comentarios                    - Obtener comentarios");
        System.out.println("POST   /posts/{id}/comentarios                    - Añadir comentario");
        System.out.println("DELETE /posts/{postId}/comentarios/{comentarioId} - Eliminar comentario");

    }
}