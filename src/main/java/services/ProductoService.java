package services;

import models.Producto;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
Servicio para la logica de negocio de productos
Gestiona el almacenamiento de memoria y operaciones CRUD
 */

public class ProductoService {
    //Inicializacion de la BBDD en memoria
    private Map<Integer, Producto> productos = new HashMap();
    private int siguienteId = 1;

    public ProductoService() {
        productos.put(1, new Producto(1, "Portatil", 999.99));
        productos.put(2, new Producto(2, "Raton", 25.50));
        productos.put(3, new Producto(3, "Teclado", 89.99));
        siguienteId = 4;
    }

    //Obtener todos los productos
    public List<Producto> obtenerTodos() {
        return new ArrayList<>(productos.values());
    }

    //obtener producto por ID
    public Producto obtenerPorId(int id) {
        return productos.get(id);
    }

    //Crear un nuevo producto
    public Producto crear(Producto producto) {
        producto.setId(siguienteId++);
        productos.put(producto.getId(), producto);
        return producto;
    }

    //Actualizar un producto existente
    public Producto actualizar(Producto producto) {
        productos.put(producto.getId(), producto);
        return producto;
    }


    //Eliminar un producto
    public boolean eliminar(int id) {
        return productos.remove(id) != null;
    }

}