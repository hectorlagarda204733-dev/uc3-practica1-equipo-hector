package com.itson.ciberseguridad.controller;

import com.itson.ciberseguridad.model.Pedido;
import com.itson.ciberseguridad.service.PedidoService;
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
 * Los endpoints de esta API aceptan el encabezado "X-User-Id", que simula al
 * usuario autenticado (por ejemplo, X-User-Id: 1 es Ana y X-User-Id: 2 es Beto).
 * La version VULNERABLE lo IGNORA por completo: ni siquiera lo lee ni lo usa
 * para autorizar el acceso a los recursos. En la Practica 2 (P2) este mecanismo
 * se sustituye por autenticacion real con JWT (Spring Security).
 */
@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    /**
     * GET /api/pedidos
     * ⚠️ VULNERABLE (BOLA): lista los pedidos de todos los usuarios, sin control
     * de acceso por usuario autenticado.
     */
    @GetMapping
    public List<Pedido> listar() {
        return pedidoService.listarTodos();
    }

    /**
     * GET /api/pedidos/{id}
     * ⚠️ VULNERABLE (BOLA + exposicion de datos): devuelve el pedido sin
     * verificar propietario y serializa la ENTIDAD completa, incluyendo los
     * datos del usuario dueño, incluida su contrasena.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> obtenerPorId(@PathVariable Long id) {
        return pedidoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /api/pedidos
     * ⚠️ VULNERABLE (BOLA de escritura + validacion inadecuada): el usuario dueño
     * se toma directamente del cuerpo JSON y no hay validacion de campos
     * (descripcion vacia, montos negativos, etc. se aceptan sin reclamar).
     */
    @PostMapping
    public Pedido crear(@RequestBody Pedido pedido) {
        return pedidoService.crear(pedido);
    }
}
