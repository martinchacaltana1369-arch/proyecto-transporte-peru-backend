package com.transporte.peru.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservaDetalleDTO {
    private Integer idReserva;
    private String nombresApellidos;
    private String ciudadOrigen;
    private String ciudadDestino;
    private String tipoClase;
    private Double precio;
    private LocalDate fechaViaje;
    private LocalTime horaViaje;
    private String nroAsiento;
    private String estado;
    private Integer idUsuario;
}