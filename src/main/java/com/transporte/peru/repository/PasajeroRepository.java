package com.transporte.peru.repository;

import com.transporte.peru.models.Pasajero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PasajeroRepository extends JpaRepository<Pasajero, String> {
    
    boolean existsByDniPasajero(String dni);
    
    @Query("SELECT p FROM Pasajero p JOIN Reserva r ON p.dniPasajero = r.pasajero.dniPasajero WHERE r.idReserva = :idReserva")
    Optional<Pasajero> findByReservaId(@Param("idReserva") Integer idReserva);
}