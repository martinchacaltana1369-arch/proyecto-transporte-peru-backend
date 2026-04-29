package com.transporte.peru.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CIUDAD")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ciudad {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CIUDAD")
    private Integer idCiudad;
    
    @Column(name = "NOMBRE_CIUDAD", nullable = false, length = 100)
    private String nombreCiudad;
    
    // Relaciones
    @JsonIgnore 
    @OneToMany(mappedBy = "ciudadOrigen")
    private List<Reserva> reservasOrigen;
    
    @JsonIgnore 
    @OneToMany(mappedBy = "ciudadDestino")
    private List<Reserva> reservasDestino;
    
    @JsonIgnore 
    @OneToMany(mappedBy = "ciudadOrigen")
    private List<BoletaElectronica> boletasOrigen;
    
    @JsonIgnore 
    @OneToMany(mappedBy = "ciudadDestino")
    private List<BoletaElectronica> boletasDestino;
}