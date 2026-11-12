package com.itson.ciberseguridad.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidad Usuario (version VULNERABLE).
 */
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String email;

    // ⚠️ VULNERABLE (exposicion de datos): la contrasena NO tiene @JsonIgnore,
    // por lo que se serializa en todas las respuestas JSON que incluyen a Usuario.
    private String password;

    // ⚠️ VULNERABLE (Mass Assignment): "rol" es un campo de la entidad y se enlaza
    // directamente desde el cuerpo JSON en POST /api/usuarios. Un cliente puede
    // enviar "rol":"ADMIN" y elevar sus privilegios.
    private String rol = "USER";

    public Usuario() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
