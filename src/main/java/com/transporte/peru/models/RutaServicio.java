package com.transporte.peru.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "RUTA_SERVICIO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RutaServicio {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NRO_RUTA_SERVICIO")
    private Integer nroRutaServicio;
    
    @Column(name = "DESCRIPCION_RUTA", nullable = false, length = 200)
    private String descripcionRuta;
    
    // Relaciones
    @JsonIgnore 
    @OneToMany(mappedBy = "rutaServicio")
    private List<Reserva> reservas;
    
    @JsonIgnore 
    @OneToMany(mappedBy = "rutaServicio")
    private List<ServicioBoleta> servicios;
}