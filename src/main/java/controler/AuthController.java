package controler;


import com.google.gson.Gson;
import io.javalin.http.Context;
import services.AuthService;
import dto.*;
import models.Usuario;

import java.util.Map;

public class AuthController {
    private final AuthService authService;
    private final Gson gson;

    public AuthController(AuthService authService) {
        this.authService = authService;
        this.gson = new Gson();
    }

    /**
     * Registra un nuevo usuario
     * @param ctx contexto con body {username, password, email}
     */
    public void registrar(Context ctx) {
        try {
            RegistroRequest request = gson.fromJson(ctx.body(), RegistroRequest.class);
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
            LoginRequest request = gson.fromJson(ctx.body(), LoginRequest.class);
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
