package com.itson.ciberseguridad.repository;

import com.itson.ciberseguridad.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
