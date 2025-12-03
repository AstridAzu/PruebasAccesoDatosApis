
import Exceptions.DatosInvalidosException;
import Exceptions.ErrorResponse;
import Exceptions.TareaNoEncontradaException;
import controler.TareaController;
import io.javalin.Javalin;
import services.TareaService;

public class mainAPIGestiónTareasEjercicio1 {
    public static void main(String[] args) {

        // Crear instancia del servicio
        TareaService tareaService = new TareaService();

        // Crear instancia del controlador
        TareaController tareaController = new TareaController(tareaService);

        // Configurar y arrancar Javalin
        Javalin app = Javalin.create(config -> {
            // Habilitar CORS
            config.plugins.enableCors(cors -> cors.add(it -> it.anyHost()));

            // Habilitar logs de desarrollo
            config.plugins.enableDevLogging();
        }).start(7070);

        // ===== MANEJADORES DE EXCEPCIONES GLOBALES =====

        // Handler para TareaNoEncontradaException (404)
        Javalin exception = app.exception( TareaNoEncontradaException.class, (e, ctx) -> {
            ctx.status(404);
            ctx.json(new ErrorResponse(e.getMessage(), 404));
            System.err.println(" 404: " + e.getMessage());
        });

        // Handler para DatosInvalidosException (400)
        app.exception(DatosInvalidosException.class, (e, ctx) -> {
            ctx.status(400);

            ctx.json(new ErrorResponse(e.getMessage(), 400));
            System.err.println(" 400: " + e.getMessage());
        });

        // Handler para cualquier otra excepción (500)
        app.exception(Exception.class, (e, ctx) -> {
            ctx.status(500);
            ctx.json(new ErrorResponse("Error interno del servidor", 500));
            System.err.println(" 500: " + e.getMessage());
            e.printStackTrace();
        });

        // ===== DEFINICIÓN DE RUTAS =====

        // GET /tareas - Obtener todas las tareas
        app.get("/tareas", tareaController::obtenerTodas);

        // GET /tareas/{id} - Obtener una tarea específica
        app.get("/tareas/{id}", tareaController::obtenerPorId);

        // POST /tareas - Crear nueva tarea
        app.post("/tareas", tareaController::crear);

        // PUT /tareas/{id} - Actualizar tarea
        app.put("/tareas/{id}", tareaController::actualizar);

        // DELETE /tareas/{id} - Eliminar tarea
        app.delete("/tareas/{id}", tareaController::eliminar);

        // PATCH /tareas/{id}/completar - Marcar como completada
        app.patch("/tareas/{id}/completar", tareaController::marcarCompletada);

        // Mensaje de bienvenida
        System.out.println("\n Servidor iniciado en http://localhost:7070");
        System.out.println(" API de Gestión de Tareas");
        System.out.println("");
        imprimirEndpoints();
    }

    private static void imprimirEndpoints() {
        System.out.println("GET    /tareas              - Obtener todas las tareas");
        System.out.println("GET    /tareas/{id}         - Obtener tarea por ID");
        System.out.println("POST   /tareas              - Crear nueva tarea");
        System.out.println("PUT    /tareas/{id}         - Actualizar tarea");
        System.out.println("DELETE /tareas/{id}         - Eliminar tarea");
        System.out.println("PATCH  /tareas/{id}/completar - Marcar como completada");
    }
}
