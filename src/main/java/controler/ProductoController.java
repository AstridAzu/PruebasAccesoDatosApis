package controler;



/*
Controlador para endpoints de productos
Maneja las peticiones HTTP relacionadas con Productos
 */

import models.Producto;
import services.ProductoService;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.Map;

public class ProductoController {
    private static ProductoService servicio = new ProductoService();

    //Registro de todas las rutas de productos en la aplicacion
    public static void registrarRutas(Javalin app) {
        //Rutas API REST
        app.get("/api/productos", ProductoController::obtenerTodos);
        app.get("/api/productos/{id}", ProductoController::obtenerPorId);
        app.post("/api/productos", ProductoController::crear);
        app.put("/api/productos/{id}", ProductoController::actualizar);
        app.delete("/api/productos/{id}", ProductoController::eliminar);
    }

    //Obtener todos los productos
    private static void obtenerTodos(Context ctx){
        ctx.json(servicio.obtenerTodos());
    }

    //Obtener producto por Id
    private static void obtenerPorId(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("id"));
        Producto producto = servicio.obtenerPorId(id);

        if(producto != null){
            ctx.json(producto);
        }else{
            ctx.status(400).json(Map.of("error", "El producto no se encuentra"));
        }
    }

    //Crear nuevo producto
    private static void crear(Context ctx){
        Producto producto = ctx.bodyAsClass(Producto.class);

        //Validaciones
        if(producto.getNombre() == null || producto.getNombre().trim().isEmpty()){
            ctx.status(400).json(Map.of("error", "El nombre es requerido"));
        }

        if(producto.getPrecio() <= 0){
            ctx.status(400).json(Map.of("error", "El precio debe ser mayor o igual a 0"));
        }

        Producto creado = servicio.crear(producto);
        ctx.status(201).json(creado);
    }

    private static void actualizar(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("id"));
        Producto producto = ctx.bodyAsClass(Producto.class);

        //validar si producto existe
        if(servicio.obtenerPorId(id) == null){
            ctx.status(400).json(Map.of("error", "El producto no existe"));
        }

        if(producto.getNombre() == null || producto.getNombre().trim().isEmpty()){
            ctx.status(400).json(Map.of("error", "El nombre es requerido"));
        }

        if(producto.getPrecio() <= 0){
            ctx.status(400).json(Map.of("error", "El precio debe ser mayor o igual a 0"));
        }

        producto.setId(id);
        Producto actualizado = servicio.actualizar(producto);
        ctx.json(actualizado);

    }

    private static void eliminar(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        if (servicio.eliminar(id)) {
            ctx.status(204);
        } else {
            ctx.status(404).json(Map.of("error", "El producto no se encuentra"));
        }
    }
}
