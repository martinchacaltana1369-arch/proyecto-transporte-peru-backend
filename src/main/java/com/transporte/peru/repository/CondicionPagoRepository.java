package com.transporte.peru.repository;

import com.transporte.peru.models.CondicionPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CondicionPagoRepository extends JpaRepository<CondicionPago, Integer> {
}