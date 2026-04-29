package com.transporte.peru.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ReservaRequest {
    private Integer idUsuario;
    private String dniPasajero;
    private String nombrePasajero;
    private String apellidosPasajero;
    private String direccionPasajero;
    private String telefonoPasajero;
    private Integer idCiudadOrigen;
    private Integer idCiudadDestino;
    private Integer codClase;
    private LocalDate fechaViaje;
    private LocalTime horaViaje;
    private String nroAsiento;
}