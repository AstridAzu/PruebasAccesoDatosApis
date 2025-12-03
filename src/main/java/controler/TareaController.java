package controler;

import dto.TareaDTO;
import io.javalin.http.Context;
import models.Tarea;
import services.TareaService;



public class TareaController {

    private final TareaService tareaService;

    public TareaController(TareaService tareaService) {
        this.tareaService = tareaService;
    }

    /*
     * GET /tareas
     * Obtiene todas las tareas
     */
    public void obtenerTodas(Context ctx) {
        System.out.println("📋 GET /tareas - Obteniendo todas las tareas");

        var tareas = tareaService.obtenerTodas();

        ctx.status(200);  // 200 OK
        ctx.json(tareas);

        System.out.println(" Devueltas " + tareas.size() + " tareas");
    }

    /*
     * GET /tareas/{id}
     * Obtiene una tarea por ID
     */
    public void obtenerPorId(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).get();
        System.out.println(" GET /tareas/" + id + " - Buscando tarea");

        Tarea tarea = tareaService.obtenerPorId(id);

        ctx.status(200);  // 200 OK
        ctx.json(tarea);

        System.out.println(" Tarea encontrada: " + tarea.getTitulo());
    }

    /*
     * POST /tareas
     * Crea una nueva tarea
     */
    public void crear(Context ctx) {
        System.out.println(" POST /tareas - Creando nueva tarea");

        TareaDTO tareaDTO = ctx.bodyAsClass(TareaDTO.class);

        Tarea nuevaTarea = tareaService.crear(tareaDTO);

        ctx.status(201);  // 201 Created
        ctx.json(nuevaTarea);

        System.out.println(" Tarea creada: ID=" + nuevaTarea.getId() +
                ", Título='" + nuevaTarea.getTitulo() + "'");
    }

    /**
     * PUT /tareas/{id}
     * Actualiza una tarea existente
     */
    public void actualizar(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).get();
        System.out.println("✏ PUT /tareas/" + id + " - Actualizando tarea");

        TareaDTO tareaDTO = ctx.bodyAsClass(TareaDTO.class);

        Tarea tareaActualizada = tareaService.actualizar(id, tareaDTO);

        ctx.status(200);  // 200 OK
        ctx.json(tareaActualizada);

        System.out.println(" Tarea actualizada: " + tareaActualizada.getTitulo());
    }

    /*
     * DELETE /tareas/{id}
     * Elimina una tarea
     */
    public void eliminar(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).get();
        System.out.println(" DELETE /tareas/" + id + " - Eliminando tarea");

        tareaService.eliminar(id);

        ctx.status(204);  // 204 No Content (eliminación exitosa sin contenido)

        System.out.println(" Tarea eliminada con ID: " + id);
    }

    /*
     * PATCH /tareas/{id}/completar
     * Marca una tarea como completada
     */
    public void marcarCompletada(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).get();
        System.out.println(" PATCH /tareas/" + id + "/completar - Marcando como completada");

        Tarea tarea = tareaService.marcarCompletada(id);

        ctx.status(200);  // 200 OK
        ctx.json(tarea);

        System.out.println("Tarea marcada como completada: " + tarea.getTitulo());
    }
}

