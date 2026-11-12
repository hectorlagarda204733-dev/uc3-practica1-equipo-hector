package com.itson.ciberseguridad.controller;

import com.itson.ciberseguridad.dto.UsuarioRegistroDTO;
import com.itson.ciberseguridad.dto.UsuarioRespuestaDTO;
import com.itson.ciberseguridad.excepciones.AccesoDenegadoException;
import com.itson.ciberseguridad.excepciones.RecursoNoEncontradoException;
import com.itson.ciberseguridad.model.Usuario;
import com.itson.ciberseguridad.repository.UsuarioRepository;
import com.itson.ciberseguridad.service.UsuarioAutenticadoService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * NOTA DIDACTICA (autenticacion simulada):
 * El encabezado "X-User-Id" simula al usuario autenticado. En P2 se sustituye
 * por JWT real con Spring Security.
 */
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioAutenticadoService usuarioAutenticadoService;

    public UsuarioController(UsuarioRepository usuarioRepository, UsuarioAutenticadoService usuarioAutenticadoService) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioAutenticadoService = usuarioAutenticadoService;
    }

    /**
     * GET /api/usuarios
     * ✅ SEGURO: salida mediante UsuarioRespuestaDTO (sin contrasena).
     */
    @GetMapping
    public ResponseEntity<List<UsuarioRespuestaDTO>> listar() {
        List<UsuarioRespuestaDTO> usuarios = usuarioRepository.findAll().stream()
                .map(this::aRespuestaDTO)
                .toList();
        return ResponseEntity.ok(usuarios);
    }

    /**
     * GET /api/usuarios/{id}
     * ✅ SEGURO: un usuario solo puede consultar su propia informacion; si
     * solicita la de otro, 403 (mitiga BOLA, API1:2023). Sin contrasena en la
     * respuesta (DTO de salida).
     */
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioRespuestaDTO> obtenerPorId(
            @PathVariable Long id,
            @RequestHeader(value = "X-User-Id", required = false) String encabezadoXUserId) {
        Usuario usuario = usuarioAutenticadoService.obtenerUsuarioAutenticado(encabezadoXUserId);

        if (!usuario.getId().equals(id)) {
            throw new AccesoDenegadoException("No tiene permisos para consultar la información de otro usuario");
        }
        Usuario buscado = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado: " + id));
        return ResponseEntity.ok(aRespuestaDTO(buscado));
    }

    /**
     * POST /api/usuarios
     * ✅ SEGURO: entrada validada con @Valid + UsuarioRegistroDTO. El DTO no
     * incluye "id" ni "rol", por lo que un cliente no puede autoasignarse
     * privilegios (mitiga Mass Assignment, API3:2023). El rol se fija en el
     * servidor y el esquema estricto de Jackson rechaza campos desconocidos.
     */
    @PostMapping
    public ResponseEntity<UsuarioRespuestaDTO> registrar(@Valid @RequestBody UsuarioRegistroDTO dto) {
        Usuario nuevo = new Usuario();
        nuevo.setNombre(dto.getNombre());
        nuevo.setEmail(dto.getEmail());
        nuevo.setPassword(dto.getPassword());
        nuevo.setRol("USER"); // ✅ SEGURO: rol fijado por el servidor, nunca por el cliente.
        Usuario guardado = usuarioRepository.save(nuevo);
        return ResponseEntity.status(201).body(aRespuestaDTO(guardado));
    }

    /**
     * ✅ SEGURO: conversion explicita entidad -> DTO de salida (sin password).
     */
    private UsuarioRespuestaDTO aRespuestaDTO(Usuario usuario) {
        return new UsuarioRespuestaDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getRol());
    }
}
