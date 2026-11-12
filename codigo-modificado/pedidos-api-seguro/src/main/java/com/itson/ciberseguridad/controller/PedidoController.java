package com.itson.ciberseguridad.controller;

import com.itson.ciberseguridad.dto.PedidoCreacionDTO;
import com.itson.ciberseguridad.dto.PedidoRespuestaDTO;
import com.itson.ciberseguridad.model.Pedido;
import com.itson.ciberseguridad.model.Usuario;
import com.itson.ciberseguridad.service.PedidoService;
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
 * El encabezado "X-User-Id" simula al usuario autenticado y se resuelve en
 * UsuarioAutenticadoService. En la Practica 2 (P2) se sustituye por JWT real
 * con Spring Security; los endpoints no cambian su contrato.
 */
@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;
    private final UsuarioAutenticadoService usuarioAutenticadoService;

    public PedidoController(PedidoService pedidoService, UsuarioAutenticadoService usuarioAutenticadoService) {
        this.pedidoService = pedidoService;
        this.usuarioAutenticadoService = usuarioAutenticadoService;
    }

    /**
     * GET /api/pedidos
     * ✅ SEGURO: lista solo los pedidos del usuario autenticado (anti-BOLA).
     */
    @GetMapping
    public ResponseEntity<List<PedidoRespuestaDTO>> listar(
            @RequestHeader(value = "X-User-Id", required = false) String encabezadoXUserId) {
        Usuario usuario = usuarioAutenticadoService.obtenerUsuarioAutenticado(encabezadoXUserId);
        List<PedidoRespuestaDTO> pedidos = pedidoService.listarDelUsuario(usuario).stream()
                .map(pedidoService::aRespuestaDTO)
                .toList();
        return ResponseEntity.ok(pedidos);
    }

    /**
     * GET /api/pedidos/{id}
     * ✅ SEGURO: verifica pedido.usuario.id == usuario autenticado; si no, 403
     * (mitiga BOLA, API1:2023). Si el usuario no se identifica, 401.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PedidoRespuestaDTO> obtenerPorId(
            @PathVariable Long id,
            @RequestHeader(value = "X-User-Id", required = false) String encabezadoXUserId) {
        Usuario usuario = usuarioAutenticadoService.obtenerUsuarioAutenticado(encabezadoXUserId);
        Pedido pedido = pedidoService.obtenerDelUsuario(id, usuario);
        return ResponseEntity.ok(pedidoService.aRespuestaDTO(pedido));
    }

    /**
     * POST /api/pedidos
     * ✅ SEGURO: entrada validada con @Valid + PedidoCreacionDTO (sin campo
     * usuario) y el dueño SIEMPRE es el usuario autenticado del encabezado
     * X-User-Id. Si falta, 401.
     */
    @PostMapping
    public ResponseEntity<PedidoRespuestaDTO> crear(
            @Valid @RequestBody PedidoCreacionDTO dto,
            @RequestHeader(value = "X-User-Id", required = false) String encabezadoXUserId) {
        Usuario usuario = usuarioAutenticadoService.obtenerUsuarioAutenticado(encabezadoXUserId);
        PedidoRespuestaDTO creado = pedidoService.crear(dto, usuario);
        return ResponseEntity.status(201).body(creado);
    }
}
