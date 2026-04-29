package com.transporte.peru.repository;

import com.transporte.peru.models.Clase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ClaseRepository extends JpaRepository<Clase, Integer> {
    
    @Query("SELECT c.tipoClase FROM Clase c WHERE c.codClase = :id")
    Optional<String> findTipoClaseById(@Param("id") Integer id);
}