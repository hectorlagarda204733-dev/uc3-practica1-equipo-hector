package com.itson.ciberseguridad.seguridad;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/**
 * PRUEBAS DE SEGURIDAD - Validacion de esquemas con Bean Validation
 * (mitiga Validacion inadecuada, API8:2023 Security Misconfiguration).
 *
 * Cada caso debe responder 400 e incluir el mensaje de validacion del campo.
 */
@SpringBootTest
@AutoConfigureMockMvc
class PruebaValidacionTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void emailInvalido_retorna400ConMensaje() throws Exception {
        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"nombre":"Elena","email":"correo-invalido","password":"claveSegura1"}
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.email").value("El correo electrónico no tiene un formato válido"));
    }

    @Test
    void passwordCorto_retorna400ConMensaje() throws Exception {
        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"nombre":"Fernando","email":"fernando.p1@itson.mx","password":"123"}
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.password")
                        .value("La contraseña debe tener entre 8 y 64 caracteres"));
    }

    @Test
    void nombreVacio_retorna400ConMensaje() throws Exception {
        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"nombre":"","email":"gabriela.p1@itson.mx","password":"claveSegura1"}
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.nombre").value("El nombre es obligatorio"));
    }
}
