package mains.ejercicio3Opcional.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Venta {
    private String id;
    private String producto;
    private int cantidad;
    private double precioUnitario;
    private LocalDateTime fecha;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    public Venta(String id, String producto, int cantidad, double precioUnitario) {
        this.id = id;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.fecha = LocalDateTime.now();
    }

    public double getTotal() {
        return cantidad * precioUnitario;
    }

    // Getters y Setters
    public String getId() { return id; }
    public String getProducto() { return producto; }
    public int getCantidad() { return cantidad; }
    public double getPrecioUnitario() { return precioUnitario; }
    public LocalDateTime getFecha() { return fecha; }

    // Getter especial para JSON (retorna String formateado)
    public String getFechaFormateada() {
        return fecha.format(FORMATTER);
    }

    public void setId(String id) { this.id = id; }
    public void setProducto(String producto) { this.producto = producto; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public void setPrecioUnitario(double precioUnitario) { this.precioUnitario = precioUnitario; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}
