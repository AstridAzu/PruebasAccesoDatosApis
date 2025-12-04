package mains.Ejercicio1Opcional.exceptions;

/*
 * Excepción personalizada: Datos inválidos (400)
 */
public class DatosInvalidosException extends RuntimeException {
    public DatosInvalidosException(String mensaje) {
        super(mensaje);
    }
}
