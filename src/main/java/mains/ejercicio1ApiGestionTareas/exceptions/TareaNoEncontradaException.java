package mains.ejercicio1ApiGestionTareas.exceptions;

public class TareaNoEncontradaException extends RuntimeException {
    public TareaNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}
