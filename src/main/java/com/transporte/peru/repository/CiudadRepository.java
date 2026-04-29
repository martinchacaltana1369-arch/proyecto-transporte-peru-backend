package com.transporte.peru.repository;

import com.transporte.peru.models.Ciudad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CiudadRepository extends JpaRepository<Ciudad, Integer> {
    
    @Query("SELECT c.nombreCiudad FROM Ciudad c WHERE c.idCiudad = :id")
    Optional<String> findNombreCiudadById(@Param("id") Integer id);
    
    @Procedure(procedureName = "sp_listar_ciudades_mas_visitadas")
    List<Object[]> ciudadesMasVisitadas();
}