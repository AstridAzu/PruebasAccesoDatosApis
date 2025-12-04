package mains.Ejercicio1Opcional.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Entidad Post (uno-a-muchos con Comentarios)
 */
public class Post {
    private int id;
    private String titulo;
    private String contenido;
    private String autor;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaPublicacion;

    private List<Comentario> comentarios;

    public Post(int id, String titulo, String contenido, String autor,
                LocalDateTime fechaPublicacion, List<Comentario> comentarios) {
        this.id = id;
        this.titulo = titulo;
        this.contenido = contenido;
        this.autor = autor;
        this.fechaPublicacion = fechaPublicacion;
        this.comentarios = comentarios;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public LocalDateTime getFechaPublicacion() { return fechaPublicacion; }
    public void setFechaPublicacion(LocalDateTime fechaPublicacion) { this.fechaPublicacion = fechaPublicacion; }

    public List<Comentario> getComentarios() { return comentarios; }
    public void setComentarios(List<Comentario> comentarios) { this.comentarios = comentarios; }
}
