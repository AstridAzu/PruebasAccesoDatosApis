//importacion de la libreria



/*
ejemplo 4 respuesta de join con javalin
ejemplo de serializacion  automatica de o0bjeto java a json
javalin utiliza jacjson para  convertir el objeto  Map  en JSON
 */
//http://localhost:7070/

import io.javalin.Javalin;

import java.util.Map;

public class Ejemplo4JaisonJavalin {
    public static void main(String[] args) {
        //creacion del empoint con javalin en el puerto 7070
        Javalin app = Javalin.create().start(7070);

        //muestra u8n usuario con sus carcteristicas
        app.get("/usuario", ctx -> {
            Map<String, Object> reusuarioss = Map.of(
                    "id",1,
                    "nombre", "astrid",
                    "edad", 28
            );
            ctx.json(reusuarioss);

        });

        System.out.println("servidor inicado  en  http://localhost:7070/");
        System.out.println("Prueba: http://localhost:7070/usuario");
    }}