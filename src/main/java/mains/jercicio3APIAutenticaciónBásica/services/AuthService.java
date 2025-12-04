package mains.jercicio3APIAutenticaciónBásica.services;

import mains.jercicio3APIAutenticaciónBásica.dto.*;
import mains.jercicio3APIAutenticaciónBásica.models.Usuario;
import mains.jercicio3APIAutenticaciónBásica.repositories.TokenRepository;
import mains.jercicio3APIAutenticaciónBásica.repositories.UsuarioRepository;

import java.util.Optional;

public class AuthService {
    private final UsuarioRepository usuarioRepository;
    private final TokenRepository tokenRepository;

    public AuthService(UsuarioRepository usuarioRepository, TokenRepository tokenRepository) {
        this.usuarioRepository = usuarioRepository;
        this.tokenRepository = tokenRepository;
    }

    /**
     * Registra un nuevo usuario
     * @throws IllegalArgumentException si el usuario ya existe
     */
    public Usuario registrarUsuario(RegistroRequest request) {
        // Validaciones de negocio
        if (request.getUsername() == null || request.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username es requerido");
        }

        if (request.getPassword() == null || request.getPassword().length() < 6) {
            throw new IllegalArgumentException("Password debe tener al menos 6 caracteres");
        }

        if (request.getEmail() == null || !request.getEmail().contains("@")) {
            throw new IllegalArgumentException("Email inválido");
        }

        if (usuarioRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("El username ya está en uso");
        }

        // NOTA: En producción usar BCrypt.hashpw(password, BCrypt.gensalt())
        Usuario nuevoUsuario = new Usuario(
                request.getUsername(),
                request.getPassword(),
                request.getEmail()
        );

        return usuarioRepository.save(nuevoUsuario);
    }

    /**
     * Autentica un usuario y genera token
     * @throws IllegalArgumentException si las credenciales son inválidas
     */
    public AuthResponse login(LoginRequest request) {
        if (request.getUsername() == null || request.getPassword() == null) {
            throw new IllegalArgumentException("Username y password son requeridos");
        }

        Usuario usuario = usuarioRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Credenciales inválidas"));

        // NOTA: En producción usar BCrypt.checkpw(password, hashedPassword)
        if (!usuario.getPassword().equals(request.getPassword())) {
            throw new IllegalArgumentException("Credenciales inválidas");
        }

        // Generar token (en producción usar JWT)
        String token = generarToken(request.getUsername());
        tokenRepository.save(token, request.getUsername());

        return new AuthResponse(token, request.getUsername());
    }

    /**
     * Valida un token y retorna el usuario
     */
    public Optional<Usuario> validarToken(String token) {
        if (token == null || token.trim().isEmpty()) {
            return Optional.empty();
        }

        return tokenRepository.findUsernameByToken(token)
                .flatMap(usuarioRepository::findByUsername);
    }

    /**
     * Genera un token simple (username + timestamp)
     */
    private String generarToken(String username) {
        return username + "_" + System.currentTimeMillis();
    }

    /**
     * Cierra sesión eliminando el token
     */
    public void logout(String token) {
        tokenRepository.delete(token);
    }
}
