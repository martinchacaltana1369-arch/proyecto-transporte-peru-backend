package com.transporte.peru.repository;

import com.transporte.peru.models.BoletaElectronica;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BoletaRepository extends JpaRepository<BoletaElectronica, Integer> {
    
    @Procedure(procedureName = "sp_listar_boleta")
    List<Object[]> listarBoletaPorIdReserva(@Param("id_reserva") Integer idReserva);
    
    @Procedure(procedureName= "sp_listar_boleta_por_nro")
    List<Object[]> listarBoletaPorNro(@Param("nro_boleta") Integer nroBoleta);
    
    @Procedure(procedureName = "sp_total_boletas_emitidas")
    List<Object[]> totalBoletasEmitidas();
}