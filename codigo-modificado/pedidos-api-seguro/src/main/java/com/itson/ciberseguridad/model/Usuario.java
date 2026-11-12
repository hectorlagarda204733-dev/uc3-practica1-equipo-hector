package com.itson.ciberseguridad.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidad Usuario (version SEGURA).
 */
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String email;

    // ✅ SEGURO: la contrasena nunca se serializa en las respuestas JSON
    // (@JsonIgnore); el registro se hace mediante UsuarioRegistroDTO.
    // Nota didactica: el hashing de contrasenas se introduce en P2 con JWT/Spring Security.
    @JsonIgnore
    private String password;

    // ✅ SEGURO: "rol" nunca llega desde el cuerpo de la peticion porque la
    // entrada se deserializa en UsuarioRegistroDTO (que no lo incluye).
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
