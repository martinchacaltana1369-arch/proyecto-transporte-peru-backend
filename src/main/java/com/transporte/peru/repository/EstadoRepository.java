package com.transporte.peru.repository;

import com.transporte.peru.models.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {
    
    @Modifying
    @Query("UPDATE Usuario u SET u.estado = :estado WHERE u.idUsuario = :idUsuario")
    int updateEstadoUsuario(@Param("idUsuario") Integer idUsuario, @Param("estado") Integer estado);
}