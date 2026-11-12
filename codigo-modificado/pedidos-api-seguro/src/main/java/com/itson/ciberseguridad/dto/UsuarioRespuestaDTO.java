package com.itson.ciberseguridad.dto;

/**
 * DTO de salida para usuarios (version SEGURA).
 *
 * ✅ SEGURO: expone id, nombre, email y rol; NUNCA la contrasena.
 */
public class UsuarioRespuestaDTO {

    private Long id;
    private String nombre;
    private String email;
    private String rol;

    public UsuarioRespuestaDTO() {
    }

    public UsuarioRespuestaDTO(Long id, String nombre, String email, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.rol = rol;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
