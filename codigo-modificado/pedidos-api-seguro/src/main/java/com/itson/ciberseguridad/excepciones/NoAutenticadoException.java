package com.itson.ciberseguridad.excepciones;

/**
 * Se lanza cuando el usuario autenticado no puede identificarse
 * (encabezado X-User-Id ausente, con formato invalido o inexistente).
 * El manejador global la traduce a HTTP 401.
 */
public class NoAutenticadoException extends RuntimeException {

    public NoAutenticadoException(String mensaje) {
        super(mensaje);
    }
}
