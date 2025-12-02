
//importaciones de la libreria
import io.javalin.Javalin;

//GET (select, Post(insert), PUT(update), DELETE(delete))

/*
API RESP BASICA EN JAVA CON
aplicacion simple que proporciona un endpoint tipo get
el servidor se inicia en el puerto 7070 con un mensaje en texto plano
 */
//http://localhost:7070/
public class Ejemplo1 {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7070);
        //creacion de saludo con texto plano con el estado get
        app.get("/", ctx -> {
            ctx.result("hola desde javali");
        });
        System.out.println("servidor iniciado en http://localhost:7070/");
    }


    public static class Ejemplo3parametrosJavalin {
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
}
