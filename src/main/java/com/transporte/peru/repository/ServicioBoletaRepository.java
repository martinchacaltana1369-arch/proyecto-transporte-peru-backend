package com.transporte.peru.repository;

import com.transporte.peru.models.ServicioBoleta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicioBoletaRepository extends JpaRepository<ServicioBoleta, Integer> {
}