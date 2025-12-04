package mains.Ejercicio1Opcional.controller;

import io.javalin.http.Context;
import mains.Ejercicio1Opcional.dto.ComentarioDTO;
import mains.Ejercicio1Opcional.models.Comentario;
import mains.Ejercicio1Opcional.services.ComentarioService;

public class ComentarioController {
    private final ComentarioService comentarioService;

    public ComentarioController(ComentarioService comentarioService) {
        this.comentarioService = comentarioService;
    }

    public void obtenerComentarios(Context ctx) {
        int postId = ctx.pathParamAsClass("id", Integer.class).get();
        System.out.println("💬 GET /posts/" + postId + "/comentarios");

        var comentarios = comentarioService.obtenerComentariosPorPost(postId);
        ctx.status(200).json(comentarios);
        System.out.println("✅ Devueltos " + comentarios.size() + " comentarios");
    }

    public void crearComentario(Context ctx) {
        int postId = ctx.pathParamAsClass("id", Integer.class).get();
        System.out.println("➕ POST /posts/" + postId + "/comentarios");

        ComentarioDTO comentarioDTO = ctx.bodyAsClass(ComentarioDTO.class);
        Comentario nuevoComentario = comentarioService.crear(postId, comentarioDTO);

        ctx.status(201).json(nuevoComentario);
        System.out.println("✅ Comentario creado por: " + nuevoComentario.getAutor());
    }

    public void eliminarComentario(Context ctx) {
        int postId = ctx.pathParamAsClass("postId", Integer.class).get();
        int comentarioId = ctx.pathParamAsClass("comentarioId", Integer.class).get();
        System.out.println("🗑️ DELETE /posts/" + postId + "/comentarios/" + comentarioId);

        comentarioService.eliminar(postId, comentarioId);
        ctx.status(204);
        System.out.println("✅ Comentario eliminado");
    }
}
