package com.itson.ciberseguridad.config;

import com.itson.ciberseguridad.excepciones.AccesoDenegadoException;
import com.itson.ciberseguridad.excepciones.NoAutenticadoException;
import com.itson.ciberseguridad.excepciones.RecursoNoEncontradoException;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * ✅ SEGURO: manejo centralizado de errores. Las respuestas incluyen solo
 * mensajes utiles para el cliente (sin stacktrace ni detalles internos), lo
 * que reduce la superficie de informacion (API8:2023 Security Misconfiguration).
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Errores de validacion de DTOs (@Valid): 400 con un mensaje por campo.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> manejarValidacion(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errores.put(error.getField(), error.getDefaultMessage()));
        return errores;
    }

    /**
     * Cuerpo JSON ilegible (por ejemplo, campo desconocido con esquema
     * estricto de Jackson): 400 sin detalles internos de la excepcion.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> manejarCuerpoIlegible(HttpMessageNotReadableException ex) {
        return Map.of("error", "El cuerpo de la solicitud no es válido (verifique tipos y campos permitidos)");
    }

    /**
     * Encabezado X-User-Id ausente, invalido o inexistente: 401.
     */
    @ExceptionHandler(NoAutenticadoException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, String> manejarNoAutenticado(NoAutenticadoException ex) {
        return Map.of("error", ex.getMessage());
    }

    /**
     * Recurso ajeno al usuario autenticado: 403.
     */
    @ExceptionHandler(AccesoDenegadoException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Map<String, String> manejarAccesoDenegado(AccesoDenegadoException ex) {
        return Map.of("error", ex.getMessage());
    }

    /**
     * Recurso inexistente: 404.
     */
    @ExceptionHandler(RecursoNoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> manejarRecursoNoEncontrado(RecursoNoEncontradoException ex) {
        return Map.of("error", ex.getMessage());
    }

    /**
     * Ultima linea de defensa: 500 generico sin exponer la causa interna.
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> manejarErrorGenerico(Exception ex) {
        return Map.of("error", "Error interno del servidor");
    }
}
