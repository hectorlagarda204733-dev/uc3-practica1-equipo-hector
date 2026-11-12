package com.itson.ciberseguridad.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * DTO de entrada para la creacion de pedidos (version SEGURA).
 *
 * ✅ SEGURO: NO incluye el campo "usuario". El dueño del pedido NUNCA se toma
 * del cuerpo JSON: el servicio lo resuelve a partir del encabezado X-User-Id
 * del usuario autenticado (mitiga BOLA de escritura, API1:2023).
 */
public class PedidoCreacionDTO {

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 200, message = "La descripción no puede exceder 200 caracteres")
    private String descripcion;

    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser mayor a cero")
    @DecimalMax(value = "100000.00", message = "El monto no puede exceder 100000.00")
    private Double monto;

    public PedidoCreacionDTO() {
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
}
