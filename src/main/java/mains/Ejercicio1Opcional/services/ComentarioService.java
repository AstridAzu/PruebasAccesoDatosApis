package mains.Ejercicio1Opcional.services;

import mains.Ejercicio1Opcional.dto.ComentarioDTO;
import mains.Ejercicio1Opcional.exceptions.ComentarioNoEncontradoException;
import mains.Ejercicio1Opcional.exceptions.DatosInvalidosException;
import mains.Ejercicio1Opcional.models.Comentario;
import mains.Ejercicio1Opcional.models.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ComentarioService {
    private final PostService postService;
    private final AtomicInteger contadorId = new AtomicInteger(1);

    public ComentarioService(PostService postService) {
        this.postService = postService;
    }

    public List<Comentario> obtenerComentariosPorPost(int postId) {
        Post post = postService.obtenerPorId(postId);
        return post.getComentarios();
    }

    public Comentario crear(int postId, ComentarioDTO comentarioDTO) {
        validarComentario(comentarioDTO);

        Post post = postService.obtenerPorId(postId);

        int nuevoId = contadorId.getAndIncrement();
        Comentario nuevoComentario = new Comentario(
                nuevoId,
                comentarioDTO.getAutor().trim(),
                comentarioDTO.getContenido().trim(),
                LocalDateTime.now()
        );

        post.getComentarios().add(nuevoComentario);
        return nuevoComentario;
    }

    public void eliminar(int postId, int comentarioId) {
        Post post = postService.obtenerPorId(postId);

        boolean eliminado = post.getComentarios().removeIf(c -> c.getId() == comentarioId);

        if (!eliminado) {
            throw new ComentarioNoEncontradoException(
                    "No se encontró el comentario con ID: " + comentarioId + " en el post " + postId
            );
        }
    }

    private void validarComentario(ComentarioDTO comentarioDTO) {
        if (comentarioDTO.getAutor() == null || comentarioDTO.getAutor().trim().isEmpty()) {
            throw new DatosInvalidosException("El autor del comentario es obligatorio");
        }
        if (comentarioDTO.getContenido() == null || comentarioDTO.getContenido().trim().isEmpty()) {
            throw new DatosInvalidosException("El contenido del comentario es obligatorio");
        }
        if (comentarioDTO.getContenido().length() > 500) {
            throw new DatosInvalidosException("El comentario no puede superar 500 caracteres");
        }
    }
}
