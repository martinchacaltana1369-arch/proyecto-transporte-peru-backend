package com.transporte.peru.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "SERVICIO_BOLETA")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServicioBoleta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COD_SERVICIO_BOLETA")
    private Integer codServicioBoleta;
    
    @Column(name = "CANTIDAD", nullable = false)
    private Integer cantidad;
    
    @Column(name = "DETALLE_SERVICIO", nullable = false, length = 200)
    private String detalleServicio;
    
    @Column(name = "PRECIO_UNITARIO_SERVICIO", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitarioServicio;
    
    // Relaciones ManyToOne
    @ManyToOne
    @JoinColumn(name = "ID_UNIDAD_MEDIDA", nullable = false)
    private UnidadMedida unidadMedida;
    
    @ManyToOne
    @JoinColumn(name = "NRO_RUTA_SERVICIO", nullable = false)
    private RutaServicio rutaServicio;
    
    // Relación ManyToMany inversa
    @JsonIgnore 
    @ManyToMany(mappedBy = "servicios")
    private List<BoletaElectronica> boletas;
    
    // Método helper
    public BigDecimal getSubtotal() {
        return this.precioUnitarioServicio.multiply(new BigDecimal(this.cantidad));
    }
}