package com.itson.ciberseguridad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Punto de entrada de la API de pedidos (version SEGURA).
 *
 * Practica 1 - Seguridad en APIs. Mitigaciones implementadas:
 * - BOLA (API1:2023): verificacion de propiedad en cada recurso.
 * - Mass Assignment (API3:2023): DTOs de entrada sin campos sensibles y
 *   esquema estricto de Jackson (rechaza propiedades desconocidas).
 * - Validacion inadecuada (API8:2023): Bean Validation en toda entrada.
 * - Exposicion de datos: @JsonIgnore en password y DTOs de salida.
 */
@SpringBootApplication
public class PedidosApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PedidosApiApplication.class, args);
    }
}
