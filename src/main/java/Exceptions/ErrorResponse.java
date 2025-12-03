package Exceptions;

/*
 * Clase para respuestas de error estandarizadas
 */
public class ErrorResponse {
    private String error;
    private int status;
    private long timestamp;

    public ErrorResponse(String error, int status) {
        this.error = error;
        this.status = status;
        this.timestamp = System.currentTimeMillis();
    }

    public String getError() { return error; }
    public int getStatus() { return status; }
    public long getTimestamp() { return timestamp; }
}
