package com.transporte.peru.repository;

import com.transporte.peru.models.RutaServicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RutaServicioRepository extends JpaRepository<RutaServicio, Integer> {
    
    Optional<RutaServicio> findByDescripcionRuta(String descripcionRuta);
    
    @Procedure(procedureName = "sp_lista_rutas_mas_vendidas")
    List<Object[]> rutasMasVendidas(@Param("fecha") LocalDate fecha);
}