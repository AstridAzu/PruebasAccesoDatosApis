package mains.ejercicio3Opcional.repositories;

import mains.ejercicio3Opcional.models.Venta;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class VentaRepository {
    private final Map<String, Venta> ventas = new HashMap<>();
    private int contadorId = 1;

    public Venta save(Venta venta) {
        if (venta.getId() == null) {
            venta.setId(String.valueOf(contadorId++));
        }
        ventas.put(venta.getId(), venta);
        return venta;
    }

    public Optional<Venta> findById(String id) {
        return Optional.ofNullable(ventas.get(id));
    }

    public List<Venta> findAll() {
        return new ArrayList<>(ventas.values());
    }

    public List<Venta> findByProducto(String producto) {
        return ventas.values().stream()
                .filter(v -> v.getProducto().equalsIgnoreCase(producto))
                .collect(Collectors.toList());
    }

    public List<Venta> findByFechaRango(LocalDateTime inicio, LocalDateTime fin) {
        return ventas.values().stream()
                .filter(v -> !v.getFecha().isBefore(inicio) && !v.getFecha().isAfter(fin))
                .collect(Collectors.toList());
    }

    public List<Venta> findByProductoAndFechaRango(String producto, LocalDateTime inicio, LocalDateTime fin) {
        return ventas.values().stream()
                .filter(v -> v.getProducto().equalsIgnoreCase(producto))
                .filter(v -> !v.getFecha().isBefore(inicio) && !v.getFecha().isAfter(fin))
                .collect(Collectors.toList());
    }
}
