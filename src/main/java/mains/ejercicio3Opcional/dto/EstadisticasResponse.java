package mains.ejercicio3Opcional.dto;

public class EstadisticasResponse {
    private double totalVentas;
    private int numeroTransacciones;
    private String productoMasVendido;
    private double ventaPromedio;
    private int cantidadTotalProductos;

    public EstadisticasResponse(double totalVentas, int numeroTransacciones,
                                String productoMasVendido, double ventaPromedio,
                                int cantidadTotalProductos) {
        this.totalVentas = Math.round(totalVentas * 100.0) / 100.0;
        this.numeroTransacciones = numeroTransacciones;
        this.productoMasVendido = productoMasVendido;
        this.ventaPromedio = Math.round(ventaPromedio * 100.0) / 100.0;
        this.cantidadTotalProductos = cantidadTotalProductos;
    }

    // Getters
    public double getTotalVentas() { return totalVentas; }
    public int getNumeroTransacciones() { return numeroTransacciones; }
    public String getProductoMasVendido() { return productoMasVendido; }
    public double getVentaPromedio() { return ventaPromedio; }
    public int getCantidadTotalProductos() { return cantidadTotalProductos; }
}
