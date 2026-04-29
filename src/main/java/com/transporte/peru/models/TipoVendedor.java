package com.transporte.peru.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TIPO_VENDEDOR")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoVendedor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COD_TIPO_VENDEDOR")
    private Integer codTipoVendedor;
    
    @Column(name = "NOMBRE_TIPO_VENDEDOR", nullable = false, length = 50)
    private String nombreTipoVendedor;
}