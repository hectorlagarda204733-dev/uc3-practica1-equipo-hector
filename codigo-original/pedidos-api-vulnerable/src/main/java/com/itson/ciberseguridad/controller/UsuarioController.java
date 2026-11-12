package com.itson.ciberseguridad.controller;

import com.itson.ciberseguridad.model.Usuario;
import com.itson.ciberseguridad.repository.UsuarioRepository;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * NOTA DIDACTICA (autenticacion simulada):
 * El encabezado "X-User-Id" simula al usuario autenticado y se documentara en
 * P2 con JWT real. La version VULNERABLE lo ignora por completo.
 */
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * GET /api/usuarios
     * ⚠️ VULNERABLE (exposicion de datos): devuelve la lista completa de
     * entidades Usuario, incluida la contrasena de cada uno.
     */
    @GetMapping
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    /**
     * GET /api/usuarios/{id}
     * ⚠️ VULNERABLE (exposicion de datos): devuelve la entidad completa con la
     * contrasena del usuario solicitado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerPorId(@PathVariable Long id) {
        return usuarioRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /api/usuarios
     * ⚠️ VULNERABLE (Mass Assignment): enlace (binding) DIRECTO del cuerpo JSON a
     * la entidad Usuario. Un cliente puede enviar "rol":"ADMIN" o incluso
     * "id":99 y el sistema lo guarda tal cual, sin validacion de esquema.
     */
    @PostMapping
    public ResponseEntity<Usuario> registrar(@RequestBody Usuario usuario) {
        Usuario guardado = usuarioRepository.save(usuario);
        return ResponseEntity.status(201).body(guardado);
    }
}
