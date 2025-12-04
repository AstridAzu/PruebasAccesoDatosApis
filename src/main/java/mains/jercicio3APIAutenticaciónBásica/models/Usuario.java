package mains.jercicio3APIAutenticaciónBásica.models;

import java.time.LocalDateTime;

public class Usuario {
    private String username;
    private String password;
    private String email;
    private LocalDateTime fechaRegistro;

    public Usuario(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.fechaRegistro = LocalDateTime.now();
    }

    // Getters
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }

    // Setters
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setEmail(String email) { this.email = email; }
}
