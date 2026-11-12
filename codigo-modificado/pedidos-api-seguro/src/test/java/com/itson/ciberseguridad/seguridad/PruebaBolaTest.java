package com.itson.ciberseguridad.seguridad;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

/**
 * PRUEBAS DE SEGURIDAD - Mitigacion de BOLA (API1:2023).
 *
 * Escenarios (datos sembrados en data.sql: Ana = usuario 1 con pedidos 1 y 2;
 * Beto = usuario 2 con pedido 3):
 *   1. Usuario 1 obtiene su propio pedido  -> 200.
 *   2. Usuario 1 solicita el pedido 3 de Beto -> 403.
 *   3. Peticion sin encabezado X-User-Id -> 401.
 */
@SpringBootTest
@AutoConfigureMockMvc
class PruebaBolaTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void usuario1_obtieneSuPropioPedido_retorna200() throws Exception {
        // ✅ SEGURO: el dueño consulta su propio recurso.
        mockMvc.perform(get("/api/pedidos/1").header("X-User-Id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombreUsuario").value("Ana"));
    }

    @Test
    void usuario1_solicitaPedidoDeBeto_retorna403() throws Exception {
        // ✅ SEGURO: el pedido 3 pertenece al usuario 2 (Beto); el usuario 1
        // recibe 403 y NO los datos del pedido ajeno.
        mockMvc.perform(get("/api/pedidos/3").header("X-User-Id", "1"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.error").value("No tiene permisos para acceder al pedido solicitado"));
    }

    @Test
    void sinEncabezadoXUserId_retorna401() throws Exception {
        // ✅ SEGURO: sin identidad simulada no hay acceso a ningun recurso.
        mockMvc.perform(get("/api/pedidos/1"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").exists());
    }

    @Test
    void usuario1_solicitaUsuarioAjeno_retorna403() throws Exception {
        // ✅ SEGURO: el usuario 1 no puede consultar la informacion del usuario 2.
        mockMvc.perform(get("/api/usuarios/2").header("X-User-Id", "1"))
                .andExpect(status().isForbidden());
    }
}
