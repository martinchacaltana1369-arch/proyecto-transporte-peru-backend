package com.transporte.peru.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TIPOS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoUsuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TIPO")
    private Integer idTipo;
    
    @Column(name = "DESCRIPCION", length = 20)
    private String descripcion;
}