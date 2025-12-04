package mains.Ejercicio1Opcional.controller;

import io.javalin.http.Context;
import mains.Ejercicio1Opcional.dto.PostDTO;
import mains.Ejercicio1Opcional.models.Post;
import mains.Ejercicio1Opcional.services.PostService;

public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    public void obtenerTodos(Context ctx) {
        System.out.println("📋 GET /posts - Obteniendo todos los posts");
        var posts = postService.obtenerTodos();
        ctx.status(200).json(posts);
        System.out.println("✅ Devueltos " + posts.size() + " posts");
    }

    public void obtenerPorId(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).get();
        System.out.println("🔍 GET /posts/" + id);

        Post post = postService.obtenerPorId(id);
        ctx.status(200).json(post);
        System.out.println("✅ Post encontrado: " + post.getTitulo());
    }

    public void crear(Context ctx) {
        System.out.println("➕ POST /posts - Creando nuevo post");
        PostDTO postDTO = ctx.bodyAsClass(PostDTO.class);

        Post nuevoPost = postService.crear(postDTO);
        ctx.status(201).json(nuevoPost);
        System.out.println("✅ Post creado: ID=" + nuevoPost.getId() + ", Título='" + nuevoPost.getTitulo() + "'");
    }

    public void actualizar(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).get();
        System.out.println("✏️ PUT /posts/" + id);

        PostDTO postDTO = ctx.bodyAsClass(PostDTO.class);
        Post postActualizado = postService.actualizar(id, postDTO);

        ctx.status(200).json(postActualizado);
        System.out.println("✅ Post actualizado: " + postActualizado.getTitulo());
    }

    public void eliminar(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).get();
        System.out.println("🗑️ DELETE /posts/" + id);

        postService.eliminar(id);
        ctx.status(204);
        System.out.println("✅ Post eliminado con ID: " + id);
    }
}
