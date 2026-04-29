package com.transporte.peru.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ESTADOS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Estado {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ESTADO")
    private Integer idEstado;
    
    @Column(name = "DESCRIPCION", length = 15)
    private String descripcion;
}