package com.itson.ciberseguridad.service;

import com.itson.ciberseguridad.dto.PedidoCreacionDTO;
import com.itson.ciberseguridad.dto.PedidoRespuestaDTO;
import com.itson.ciberseguridad.excepciones.AccesoDenegadoException;
import com.itson.ciberseguridad.excepciones.RecursoNoEncontradoException;
import com.itson.ciberseguridad.model.Pedido;
import com.itson.ciberseguridad.model.Usuario;
import com.itson.ciberseguridad.repository.PedidoRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Logica de negocio de pedidos (version SEGURA).
 */
@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    /**
     * Lista SOLO los pedidos del usuario autenticado.
     * ✅ SEGURO: consulta filtrada por propietario (mitiga BOLA, API1:2023).
     */
    public List<Pedido> listarDelUsuario(Usuario usuario) {
        return pedidoRepository.findByUsuarioId(usuario.getId());
    }

    /**
     * Obtiene un pedido por id verificando que pertenezca al usuario autenticado.
     * ✅ SEGURO: si el pedido no existe -> 404; si existe pero es de otro
     * usuario -> 403 (mitiga BOLA, API1:2023).
     */
    public Pedido obtenerDelUsuario(Long id, Usuario usuario) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Pedido no encontrado: " + id));

        if (!pedido.getUsuario().getId().equals(usuario.getId())) {
            throw new AccesoDenegadoException("No tiene permisos para acceder al pedido solicitado");
        }
        return pedido;
    }

    /**
     * Crea un pedido asignado SIEMPRE al usuario autenticado.
     * ✅ SEGURO: el dueño se toma del encabezado X-User-Id resuelto por
     * UsuarioAutenticadoService, nunca del cuerpo JSON (mitiga BOLA de escritura).
     */
    public PedidoRespuestaDTO crear(PedidoCreacionDTO dto, Usuario usuario) {
        Pedido pedido = new Pedido();
        pedido.setDescripcion(dto.getDescripcion());
        pedido.setMonto(dto.getMonto());
        pedido.setFecha(LocalDate.now());
        pedido.setUsuario(usuario);
        return aRespuestaDTO(pedidoRepository.save(pedido));
    }

    /**
     * ✅ SEGURO: conversion explicita entidad -> DTO de salida (sin password).
     */
    public PedidoRespuestaDTO aRespuestaDTO(Pedido pedido) {
        return new PedidoRespuestaDTO(
                pedido.getId(),
                pedido.getDescripcion(),
                pedido.getMonto(),
                pedido.getFecha(),
                pedido.getUsuario() != null ? pedido.getUsuario().getNombre() : null);
    }
}
