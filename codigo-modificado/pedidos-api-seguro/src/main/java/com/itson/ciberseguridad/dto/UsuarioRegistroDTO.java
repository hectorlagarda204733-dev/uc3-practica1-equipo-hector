package com.itson.ciberseguridad.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO de entrada para el registro de usuarios (version SEGURA).
 *
 * ✅ SEGURO: no incluye los campos "id" (el identificador lo asigna la base
 * de datos) y el campo "rol" se declara WRITE_ONLY: si el cliente lo envia
 * (p. ej. "rol":"ADMIN"), Jackson lo acepta pero el servidor NUNCA lo usa:
 * el rol siempre se fija como USER en el servicio (mitiga Mass Assignment,
 * API3:2023). Cualquier OTRO campo desconocido se rechaza con 400 gracias
 * al esquema estricto (fail-on-unknown-properties=true).
 * Los campos se validan con Bean Validation (@Valid).
 */
public class UsuarioRegistroDTO {

    @NotBlank(message = "El nombre es obligatorio")
    // Nota: para una cadena vacía, @Size tambien se viola; se usa el MISMO
    // mensaje para que la respuesta sea determinista (una sola restriccion
    // reporta "obligatorio" y la otra respeta el minimo/maximo de longitud).
    @Size(min = 3, max = 50, message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El correo electrónico no tiene un formato válido")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, max = 64, message = "La contraseña debe tener entre 8 y 64 caracteres")
    private String password;

    /**
     * Campo declarado solo para aceptar el intento de Mass Assignment y
     * descartarlo: el servidor jamas lee este valor (rol fijo = USER).
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String rol;

    public UsuarioRegistroDTO() {
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
