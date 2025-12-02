import io.javalin.Javalin;
//importacion de la libreria



/*
manejo de parametros de ruta com javalin
 */
//http://localhost:7070/

public class Ejemplo3parametrosJavalin {
    public static void main(String[] args) {
        //creacion del empoint con javalin en el puerto 7070
        Javalin app = Javalin.create().start(7070);

        //creacion de saludo con texto plano con el metodo get
        app.get( "/saludo/{nombre}", ctx -> {
            String nombre = ctx.pathParam("nombre");
            ctx.result("hola" + nombre);

        });

        System.out.println("servidor inicado  en  http://localhost:7070/");
        System.out.println("Prueba: http://localhost:7070/saludo/Ruben");


    }
}

