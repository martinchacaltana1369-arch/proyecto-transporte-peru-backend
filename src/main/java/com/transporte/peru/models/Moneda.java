package com.transporte.peru.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "MONEDA")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Moneda {
    
    @Id
    @Column(name = "COD_MONEDA", length = 5)
    private String codMoneda;
    
    @Column(name = "DESCRIPCION_MONEDA", nullable = false, length = 50)
    private String descripcionMoneda;
}