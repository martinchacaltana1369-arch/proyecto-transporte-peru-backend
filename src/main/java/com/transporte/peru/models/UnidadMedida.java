package com.transporte.peru.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "UNIDAD_MEDIDA")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnidadMedida {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_UNIDAD_MEDIDA")
    private Integer idUnidadMedida;
    
    @Column(name = "NOMBRE_UNIDAD", nullable = false, length = 50)
    private String nombreUnidad;
    
    @Column(name = "DESCRIPCION_UNIDAD_MEDIDA", length = 200)
    private String descripcionUnidadMedida;
}