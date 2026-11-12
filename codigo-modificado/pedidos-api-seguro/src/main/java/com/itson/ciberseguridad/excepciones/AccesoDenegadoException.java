package com.itson.ciberseguridad.excepciones;

/**
 * Se lanza cuando el recurso existe pero no pertenece al usuario autenticado.
 * El manejador global la traduce a HTTP 403.
 */
public class AccesoDenegadoException extends RuntimeException {

    public AccesoDenegadoException(String mensaje) {
        super(mensaje);
    }
}
