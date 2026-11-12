package com.itson.ciberseguridad.service;

import com.itson.ciberseguridad.excepciones.NoAutenticadoException;
import com.itson.ciberseguridad.model.Usuario;
import com.itson.ciberseguridad.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

/*
 * NOTA DIDACTICA (autenticacion simulada):
 * En esta practica el usuario autenticado se simula con el encabezado
 * "X-User-Id" (1 = Ana, 2 = Beto). Este servicio es el UNICO punto que lo
 * resuelve, de modo que en la Practica 2 (P2) la sustitucion por JWT real
 * (Spring Security) solo requiere cambiar la implementacion de esta clase.
 */
@Service
public class UsuarioAutenticadoService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioAutenticadoService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Resuelve el usuario autenticado a partir del encabezado X-User-Id.
     * ✅ SEGURO: si el encabezado falta, tiene formato invalido o el usuario no
     * existe, se rechaza la peticion con HTTP 401 (NoAutenticadoException).
     */
    public Usuario obtenerUsuarioAutenticado(String encabezadoXUserId) {
        if (encabezadoXUserId == null || encabezadoXUserId.isBlank()) {
            throw new NoAutenticadoException("No autenticado: falta el encabezado X-User-Id");
        }
        Long idUsuario;
        try {
            idUsuario = Long.parseLong(encabezadoXUserId.trim());
        } catch (NumberFormatException ex) {
            throw new NoAutenticadoException("No autenticado: el encabezado X-User-Id no es válido");
        }
        return usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new NoAutenticadoException("No autenticado: el usuario indicado en X-User-Id no existe"));
    }
}
