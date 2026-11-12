package com.itson.ciberseguridad.seguridad;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.itson.ciberseguridad.model.Usuario;
import com.itson.ciberseguridad.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/**
 * PRUEBAS DE SEGURIDAD - Mitigacion de Mass Assignment (API3:2023 BOPLA) y
 * de esquema estricto de Jackson.
 */
@SpringBootTest
@AutoConfigureMockMvc
class PruebaMassAssignmentTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    void rolAdminEnJson_seIgnoraYSeAlmacenaUSER() throws Exception {
        // ✅ SEGURO: el campo "rol" del JSON no existe en UsuarioRegistroDTO, por
        // lo que el servidor asigna "USER" sin importar lo que envie el cliente.
        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"nombre":"Carla Prueba","email":"carla.p1@itson.mx","password":"claveSegura1","rol":"ADMIN"}
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.rol").value("USER"));

        // Verificacion en la capa de persistencia: el rol almacenado es USER.
        Usuario guardado = usuarioRepository.findByEmail("carla.p1@itson.mx").orElseThrow();
        assertEquals("USER", guardado.getRol());
    }

    @Test
    void campoDesconocidoEnJson_retorna400() throws Exception {
        // ✅ SEGURO: esquema estricto de Jackson
        // (spring.jackson.deserialization.fail-on-unknown-properties=true);
        // un campo que no pertenece al DTO se rechaza con 400.
        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"nombre":"David Prueba","email":"david.p1@itson.mx","password":"claveSegura1","esAdministrador":true}
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").exists());
    }
}
