package com.transporte.peru.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "CLASE")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Clase {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COD_CLASE")
    private Integer codClase;
    
    @Column(name = "TIPO_CLASE", nullable = false, length = 50)
    private String tipoClase;
    
    @Column(name = "PRECIO", precision = 10, scale = 2)
    private BigDecimal precio;
}