package mains.Ejercicio1Opcional.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class Comentario {
    private int id;
    private String autor;
    private String contenido;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fecha;

    public Comentario(int id, String autor, String contenido, LocalDateTime fecha) {
        this.id = id;
        this.autor = autor;
        this.contenido = contenido;
        this.fecha = fecha;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}
