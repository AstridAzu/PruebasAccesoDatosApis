package mains.ejercicio3Opcional.controller;

import io.javalin.http.Context;
import mains.ejercicio3Opcional.dto.EstadisticasResponse;
import mains.ejercicio3Opcional.dto.VentaRequest;
import mains.ejercicio3Opcional.models.Venta;
import mains.ejercicio3Opcional.services.VentaService;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class VentaController {
    private final VentaService ventaService;

    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    /**
     * POST /ventas - Registrar nueva venta
     */
    public void registrarVenta(Context ctx) {
        try {
            VentaRequest request = ctx.bodyAsClass(VentaRequest.class);
            Venta venta = ventaService.registrarVenta(request);

            ctx.status(201).json(Map.of(
                    "mensaje", "Venta registrada exitosamente",
                    "id", venta.getId(),
                    "total", venta.getTotal()
            ));

        } catch (IllegalArgumentException e) {
            ctx.status(400).json(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            ctx.status(400).json(Map.of("error", "Datos inválidos"));
        }
    }

    /**
     * GET /ventas - Obtener todas las ventas (con filtro opcional)
     */
    public void obtenerVentas(Context ctx) {
        String producto = ctx.queryParam("producto");
        List<Venta> ventas = ventaService.obtenerVentas(producto);

        // Convertir a formato JSON-friendly
        List<Map<String, ? extends Serializable>> ventasJson = ventas.stream()
                .map(v -> Map.of(
                        "id", v.getId(),
                        "producto", v.getProducto(),
                        "cantidad", v.getCantidad(),
                        "precioUnitario", v.getPrecioUnitario(),
                        "fecha", v.getFechaFormateada(),
                        "total", v.getTotal()
                ))
                .collect(Collectors.toList());

        ctx.status(200).json(ventasJson);
    }

    /**
     * GET /ventas/{id} - Obtener una venta por ID
     */
    public void obtenerVentaPorId(Context ctx) {
        String id = ctx.pathParam("id");

        ventaService.obtenerVentaPorId(id)
                .ifPresentOrElse(
                        venta -> ctx.status(200).json(Map.of(
                                "id", venta.getId(),
                                "producto", venta.getProducto(),
                                "cantidad", venta.getCantidad(),
                                "precioUnitario", venta.getPrecioUnitario(),
                                "fecha", venta.getFechaFormateada(),
                                "total", venta.getTotal()
                        )),
                        () -> ctx.status(404).json(Map.of("error", "Venta no encontrada"))
                );
    }

    /**
     * GET /estadisticas - Obtener estadísticas
     */
    public void obtenerEstadisticas(Context ctx) {
        try {
            String fechaInicioStr = ctx.queryParam("fecha_inicio");
            String fechaFinStr = ctx.queryParam("fecha_fin");

            LocalDate fechaInicio = fechaInicioStr != null
                    ? LocalDate.parse(fechaInicioStr)
                    : null;

            LocalDate fechaFin = fechaFinStr != null
                    ? LocalDate.parse(fechaFinStr)
                    : null;

            EstadisticasResponse estadisticas = ventaService.calcularEstadisticas(fechaInicio, fechaFin);

            ctx.status(200).json(estadisticas);

        } catch (DateTimeParseException e) {
            ctx.status(400).json(Map.of("error", "Formato de fecha inválido. Use YYYY-MM-DD"));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("error", "Error al calcular estadísticas"));
        }
    }
}
