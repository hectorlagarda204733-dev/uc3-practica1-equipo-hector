package com.itson.ciberseguridad.service;

import com.itson.ciberseguridad.model.Pedido;
import com.itson.ciberseguridad.model.Usuario;
import com.itson.ciberseguridad.repository.PedidoRepository;
import com.itson.ciberseguridad.repository.UsuarioRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * Logica de negocio de pedidos (version VULNERABLE).
 */
@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;

    public PedidoService(PedidoRepository pedidoRepository, UsuarioRepository usuarioRepository) {
        this.pedidoRepository = pedidoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Pedido> listarTodos() {
        // ⚠️ VULNERABLE (BOLA): devuelve TODOS los pedidos de TODOS los usuarios,
        // sin filtrar por el usuario autenticado. El encabezado X-User-Id se ignora.
        return pedidoRepository.findAll();
    }

    public Optional<Pedido> obtenerPorId(Long id) {
        // ⚠️ VULNERABLE (BOLA): se busca el pedido por id SIN verificar que
        // pertenezca al usuario autenticado. Cualquier cliente puede leer el
        // pedido de otro usuario con solo cambiar el identificador en la URL.
        return pedidoRepository.findById(id);
    }

    public Pedido crear(Pedido pedido) {
        // ⚠️ VULNERABLE (BOLA de escritura): el dueño del pedido se toma del
        // cuerpo JSON ("usuario": {"id": N}) y NO del usuario autenticado.
        // Un cliente puede crear pedidos a nombre de cualquier usuario.
        // (Solo se resuelve la referencia por id para mantener la llave foranea valida.)
        if (pedido.getUsuario() != null && pedido.getUsuario().getId() != null) {
            Usuario usuario = usuarioRepository.findById(pedido.getUsuario().getId()).orElse(null);
            pedido.setUsuario(usuario);
        }
        return pedidoRepository.save(pedido);
    }
}
