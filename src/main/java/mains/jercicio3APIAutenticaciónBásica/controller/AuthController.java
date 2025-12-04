package mains.jercicio3APIAutenticaciónBásica.controller;

import io.javalin.http.Context;
import mains.jercicio3APIAutenticaciónBásica.dto.*;
import mains.jercicio3APIAutenticaciónBásica.models.Usuario;
import mains.jercicio3APIAutenticaciónBásica.services.AuthService;

import java.util.Map;

public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Registra un nuevo usuario
     * @param ctx contexto con body {username, password, email}
     */
    public void registrar(Context ctx) {
        try {
            RegistroRequest request = ctx.bodyAsClass(RegistroRequest.class);
            Usuario usuario = authService.registrarUsuario(request);

            ctx.status(201).json(Map.of(
                    "mensaje", "Usuario registrado exitosamente",
                    "username", usuario.getUsername()
            ));

        } catch (IllegalArgumentException e) {
            ctx.status(400).json(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            ctx.status(400).json(Map.of("error", "Datos inválidos"));
        }
    }

    /**
     * Realiza login y genera token simple
     * @param ctx contexto con body {username, password}
     */
    public void login(Context ctx) {
        try {
            LoginRequest request = ctx.bodyAsClass(LoginRequest.class);
            AuthResponse response = authService.login(request);

            ctx.status(200).json(response);

        } catch (IllegalArgumentException e) {
            ctx.status(401).json(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            ctx.status(400).json(Map.of("error", "Datos inválidos"));
        }
    }

    /**
     * Obtiene perfil del usuario autenticado
     * @param ctx contexto con header Authorization
     */
    public void obtenerPerfil(Context ctx) {
        Usuario usuario = ctx.attribute("usuario");

        ctx.status(200).json(Map.of(
                "username", usuario.getUsername(),
                "email", usuario.getEmail(),
                "fechaRegistro", usuario.getFechaRegistro().toString()
        ));
    }
}
