package mains.Ejercicio1Opcional.services;

import mains.Ejercicio1Opcional.dto.PostDTO;
import mains.Ejercicio1Opcional.exceptions.DatosInvalidosException;
import mains.Ejercicio1Opcional.exceptions.PostNoEncontradoException;
import mains.Ejercicio1Opcional.models.Post;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Servicio de Posts - Lógica de negocio
 */
public class PostService {
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private final AtomicInteger contadorId = new AtomicInteger(1);

    public PostService() {
        inicializarDatosEjemplo();
    }

    public List<Post> obtenerTodos() {
        return new ArrayList<>(posts.values());
    }

    public Post obtenerPorId(int id) {
        Post post = posts.get(id);
        if (post == null) {
            throw new PostNoEncontradoException("No se encontró el post con ID: " + id);
        }
        return post;
    }

    public Post crear(PostDTO postDTO) {
        validarPost(postDTO);

        int nuevoId = contadorId.getAndIncrement();
        Post nuevoPost = new Post(
                nuevoId,
                postDTO.getTitulo().trim(),
                postDTO.getContenido().trim(),
                postDTO.getAutor().trim(),
                LocalDateTime.now(),
                new ArrayList<>()
        );

        posts.put(nuevoId, nuevoPost);
        return nuevoPost;
    }

    public Post actualizar(int id, PostDTO postDTO) {
        Post postExistente = obtenerPorId(id);
        validarPost(postDTO);

        postExistente.setTitulo(postDTO.getTitulo().trim());
        postExistente.setContenido(postDTO.getContenido().trim());
        postExistente.setAutor(postDTO.getAutor().trim());

        return postExistente;
    }

    public void eliminar(int id) {
        obtenerPorId(id); // Verificar que existe
        posts.remove(id);
    }

    private void validarPost(PostDTO postDTO) {
        if (postDTO.getTitulo() == null || postDTO.getTitulo().trim().isEmpty()) {
            throw new DatosInvalidosException("El título es obligatorio");
        }
        if (postDTO.getTitulo().length() > 200) {
            throw new DatosInvalidosException("El título no puede superar 200 caracteres");
        }
        if (postDTO.getContenido() == null || postDTO.getContenido().trim().isEmpty()) {
            throw new DatosInvalidosException("El contenido es obligatorio");
        }
        if (postDTO.getAutor() == null || postDTO.getAutor().trim().isEmpty()) {
            throw new DatosInvalidosException("El autor es obligatorio");
        }
    }

    private void inicializarDatosEjemplo() {
        Post post1 = new Post(1, "Introducción a Javalin",
                "Javalin es un framework web ligero para Java y Kotlin...",
                "María González", LocalDateTime.now().minusDays(2), new ArrayList<>());

        Post post2 = new Post(2, "APIs REST con Java",
                "Las APIs REST son fundamentales en el desarrollo moderno...",
                "Carlos Ruiz", LocalDateTime.now().minusDays(1), new ArrayList<>());

        posts.put(1, post1);
        posts.put(2, post2);
        contadorId.set(3);
    }
}
