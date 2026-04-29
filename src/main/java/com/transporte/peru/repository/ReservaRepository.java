package com.transporte.peru.repository;

import com.transporte.peru.models.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
    List<Reserva> findByUsuario_IdUsuario(Integer idUsuario);
    
    @Query("SELECT COUNT(r) > 0 FROM Reserva r WHERE r.fechaViaje = :fechaViaje AND r.horaViaje = :horaViaje AND r.nroAsiento = :nroAsiento AND r.ciudadOrigen.idCiudad = :idOrigen AND r.ciudadDestino.idCiudad = :idDestino AND r.estado <> 'ANULADA' AND r.idReserva <> :idReserva")
    boolean existsAsientoOcupado(@Param("fechaViaje") java.time.LocalDate fechaViaje,
                                  @Param("horaViaje") java.time.LocalTime horaViaje,
                                  @Param("nroAsiento") String nroAsiento,
                                  @Param("idOrigen") Integer idOrigen,
                                  @Param("idDestino") Integer idDestino,
                                  @Param("idReserva") Integer idReserva);
    
    @Modifying
    @Query("UPDATE Reserva r SET r.estado = :estado WHERE r.idReserva = :id")
    int updateEstado(@Param("id") Integer id, @Param("estado") String estado);
}