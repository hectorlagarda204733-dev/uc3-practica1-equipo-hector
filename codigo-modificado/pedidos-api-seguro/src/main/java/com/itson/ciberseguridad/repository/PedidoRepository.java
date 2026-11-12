package com.itson.ciberseguridad.repository;

import com.itson.ciberseguridad.model.Pedido;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByUsuarioId(Long usuarioId);
}
