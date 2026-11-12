package com.itson.ciberseguridad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Punto de entrada de la API de pedidos (version VULNERABLE).
 *
 * Practica 1 - Seguridad en APIs.
 * Temas: BOLA (API1:2023), Mass Assignment (API3:2023) y validacion inadecuada
 * de esquemas (API8:2023).
 */
@SpringBootApplication
public class PedidosApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PedidosApiApplication.class, args);
    }
}
