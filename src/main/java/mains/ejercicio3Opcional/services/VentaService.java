package mains.ejercicio3Opcional.services;

import mains.ejercicio3Opcional.dto.EstadisticasResponse;
import mains.ejercicio3Opcional.dto.VentaRequest;
import mains.ejercicio3Opcional.models.Venta;
import mains.ejercicio3Opcional.repositories.VentaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class VentaService {
    private final VentaRepository ventaRepository;

    public VentaService(VentaRepository ventaRepository) {
        this.ventaRepository = ventaRepository;
    }

    /**
     * Registra una nueva venta
     */
    public Venta registrarVenta(VentaRequest request) {
        // Validaciones
        if (request.getProducto() == null || request.getProducto().trim().isEmpty()) {
            throw new IllegalArgumentException("El producto es requerido");
        }

        if (request.getCantidad() <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }

        if (request.getPrecioUnitario() <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a 0");
        }

        Venta nuevaVenta = new Venta(
                null,
                request.getProducto(),
                request.getCantidad(),
                request.getPrecioUnitario()
        );

        return ventaRepository.save(nuevaVenta);
    }

    /**
     * Obtiene todas las ventas o filtra por producto
     */
    public List<Venta> obtenerVentas(String producto) {
        if (producto != null && !producto.trim().isEmpty()) {
            return ventaRepository.findByProducto(producto);
        }
        return ventaRepository.findAll();
    }

    /**
     * Obtiene una venta por ID
     */
    public Optional<Venta> obtenerVentaPorId(String id) {
        return ventaRepository.findById(id);
    }

    /**
     * Calcula estadísticas de ventas
     */
    public EstadisticasResponse calcularEstadisticas(LocalDate fechaInicio, LocalDate fechaFin) {
        List<Venta> ventas = obtenerVentasPorRango(fechaInicio, fechaFin);

        if (ventas.isEmpty()) {
            return new EstadisticasResponse(0.0, 0, "N/A", 0.0, 0);
        }

        // Total de ventas
        double totalVentas = ventas.stream()
                .mapToDouble(Venta::getTotal)
                .sum();

        // Número de transacciones
        int numeroTransacciones = ventas.size();

        // Venta promedio
        double ventaPromedio = totalVentas / numeroTransacciones;

        // Producto más vendido (por cantidad)
        Map<String, Integer> ventasPorProducto = ventas.stream()
                .collect(Collectors.groupingBy(
                        Venta::getProducto,
                        Collectors.summingInt(Venta::getCantidad)
                ));

        String productoMasVendido = ventasPorProducto.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("N/A");

        // Cantidad total de productos vendidos
        int cantidadTotal = ventas.stream()
                .mapToInt(Venta::getCantidad)
                .sum();

        return new EstadisticasResponse(
                totalVentas,
                numeroTransacciones,
                productoMasVendido,
                ventaPromedio,
                cantidadTotal
        );
    }

    /**
     * Obtiene ventas por rango de fechas
     */
    private List<Venta> obtenerVentasPorRango(LocalDate fechaInicio, LocalDate fechaFin) {
        if (fechaInicio == null && fechaFin == null) {
            return ventaRepository.findAll();
        }

        LocalDateTime inicio = fechaInicio != null
                ? fechaInicio.atStartOfDay()
                : LocalDateTime.of(2000, 1, 1, 0, 0);

        LocalDateTime fin = fechaFin != null
                ? fechaFin.atTime(LocalTime.MAX)
                : LocalDateTime.now();

        return ventaRepository.findByFechaRango(inicio, fin);
    }
}
