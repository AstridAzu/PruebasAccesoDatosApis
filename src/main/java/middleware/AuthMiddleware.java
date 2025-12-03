package middleware;


import io.javalin.http.Context;
import services.AuthService;
import models.Usuario;
import java.util.Map;

public class AuthMiddleware {
    private final AuthService authService;

    public AuthMiddleware(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Handler que verifica autenticación antes de ejecutar endpoint
     * @param ctx contexto de Javalin
     */
    public void verificarAutenticacion(Context ctx) {
        String token = ctx.header("Authorization");

        if (token == null || token.trim().isEmpty()) {
            ctx.status(401).json(Map.of("error", "No autorizado. Token requerido"));
            return;
        }

        Usuario usuario = authService.validarToken(token).orElse(null);

        if (usuario == null) {
            ctx.status(401).json(Map.of("error", "Token inválido o expirado"));
            return;
        }

        // Almacenar usuario en el contexto para uso posterior
        ctx.attribute("usuario", usuario);
    }
}
