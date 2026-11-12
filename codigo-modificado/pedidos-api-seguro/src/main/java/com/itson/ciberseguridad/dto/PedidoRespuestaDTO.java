package com.itson.ciberseguridad.dto;

import java.time.LocalDate;

/**
 * DTO de salida para pedidos (version SEGURA).
 *
 * ✅ SEGURO: expone solo los datos del pedido y el NOMBRE del usuario dueño;
 * nunca la entidad completa ni datos sensibles como la contrasena.
 */
public class PedidoRespuestaDTO {

    private Long id;
    private String descripcion;
    private Double monto;
    private LocalDate fecha;
    private String nombreUsuario;

    public PedidoRespuestaDTO() {
    }

    public PedidoRespuestaDTO(Long id, String descripcion, Double monto, LocalDate fecha, String nombreUsuario) {
        this.id = id;
        this.descripcion = descripcion;
        this.monto = monto;
        this.fecha = fecha;
        this.nombreUsuario = nombreUsuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
}
