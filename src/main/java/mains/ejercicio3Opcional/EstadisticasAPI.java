package mains.ejercicio3Opcional;

import io.javalin.Javalin;
import mains.ejercicio3Opcional.controller.VentaController;
import mains.ejercicio3Opcional.repositories.VentaRepository;
import mains.ejercicio3Opcional.services.VentaService;

public class EstadisticasAPI {

    public static void main(String[] args) {
        // Dependency Injection
        VentaRepository ventaRepository = new VentaRepository();
        VentaService ventaService = new VentaService(ventaRepository);
        VentaController ventaController = new VentaController(ventaService);

        // Configurar Javalin
        Javalin app = Javalin.create(config -> {
            config.http.defaultContentType = "application/json";
        }).start(7070);

        // Endpoints
        app.post("/ventas", ventaController::registrarVenta);
        app.get("/ventas", ventaController::obtenerVentas);
        app.get("/ventas/{id}", ventaController::obtenerVentaPorId);
        app.get("/estadisticas", ventaController::obtenerEstadisticas);

        System.out.println("🚀 API de Estadísticas iniciada en http://localhost:7070");
        System.out.println("📊 Endpoints disponibles:");
        System.out.println("   POST   /ventas");
        System.out.println("   GET    /ventas");
        System.out.println("   GET    /ventas?producto=X");
        System.out.println("   GET    /ventas/{id}");
        System.out.println("   GET    /estadisticas");
        System.out.println("   GET    /estadisticas?fecha_inicio=YYYY-MM-DD&fecha_fin=YYYY-MM-DD");
    }
}

