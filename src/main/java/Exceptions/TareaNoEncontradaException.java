package Exceptions;

public  class TareaNoEncontradaException extends RuntimeException {
    public TareaNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}
