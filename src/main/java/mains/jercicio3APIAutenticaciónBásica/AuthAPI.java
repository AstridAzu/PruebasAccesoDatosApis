package mains.jercicio3APIAutenticaciónBásica;

import io.javalin.Javalin;
import mains.jercicio3APIAutenticaciónBásica.controller.AuthController;
import mains.jercicio3APIAutenticaciónBásica.middleware.AuthMiddleware;
import mains.jercicio3APIAutenticaciónBásica.repositories.TokenRepository;
import mains.jercicio3APIAutenticaciónBásica.repositories.UsuarioRepository;
import mains.jercicio3APIAutenticaciónBásica.services.AuthService;

public class AuthAPI {

    public static void main(String[] args) {
        // Inicializar dependencias (Dependency Injection manual)
        UsuarioRepository usuarioRepository = new UsuarioRepository();
        TokenRepository tokenRepository = new TokenRepository();
        AuthService authService = new AuthService(usuarioRepository, tokenRepository);
        AuthController authController = new AuthController(authService);
        AuthMiddleware authMiddleware = new AuthMiddleware(authService);

        // Configurar Javalin
        Javalin app = Javalin.create(config -> {
            config.http.defaultContentType = "application/json";
        }).start(7070);

        // Endpoints públicos
        app.post("/auth/registrar", authController::registrar);
        app.post("/auth/login", authController::login);

        // Endpoints protegidos
        app.before("/perfil", authMiddleware::verificarAutenticacion);
        app.get("/perfil", authController::obtenerPerfil);

        System.out.println("🚀 Servidor iniciado en http://localhost:7070");
        System.out.println("📁 Arquitectura: Model-Service-Repository-Controller");
    }
}
