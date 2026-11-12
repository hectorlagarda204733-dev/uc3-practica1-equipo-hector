package com.itson.ciberseguridad.repository;

import com.itson.ciberseguridad.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
