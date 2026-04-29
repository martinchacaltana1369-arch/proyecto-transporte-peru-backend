package com.transporte.peru.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CONDICION_PAGO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CondicionPago {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CONDICION_PAGO")
    private Integer idCondicionPago;
    
    @Column(name = "NOMBRE_CONDICION", nullable = false, length = 100)
    private String nombreCondicion;
}