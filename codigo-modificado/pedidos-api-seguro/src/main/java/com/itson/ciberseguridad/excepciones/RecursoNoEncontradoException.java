package com.itson.ciberseguridad.excepciones;

/**
 * Se lanza cuando el recurso solicitado no existe.
 * El manejador global la traduce a HTTP 404.
 */
public class RecursoNoEncontradoException extends RuntimeException {

    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
