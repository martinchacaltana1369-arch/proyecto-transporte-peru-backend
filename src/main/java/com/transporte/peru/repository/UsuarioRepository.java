package com.transporte.peru.repository;

import com.transporte.peru.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByCorreoAndContrasena(String correo, String contrasena);
    Optional<Usuario> findByCorreo(String correo);
    boolean existsByCorreo(String correo);
}