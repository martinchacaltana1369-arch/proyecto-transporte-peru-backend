package com.transporte.peru.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "PASAJERO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pasajero {
    
    @Id
    @Column(name = "DNI_PASAJERO", length = 20)
    private String dniPasajero;
    
    @Column(name = "NOMBRE_PASAJERO", nullable = false, length = 100)
    private String nombrePasajero;
    
    @Column(name = "APELLIDOS_PASAJERO", nullable = false, length = 100)
    private String apellidosPasajero;
    
    @Column(name = "DIRECCION_PASAJERO", length = 200)
    private String direccionPasajero;
    
    @Column(name = "TELEFONO_PASAJERO", length = 20)
    private String telefonoPasajero;
    
    // Relaciones
    @JsonIgnore 
    @OneToMany(mappedBy = "pasajero")
    private List<Reserva> reservas;
    
    @JsonIgnore 
    @OneToMany(mappedBy = "pasajero")
    private List<BoletaElectronica> boletas;
}