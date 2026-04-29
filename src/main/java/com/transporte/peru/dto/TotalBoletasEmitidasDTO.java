package com.transporte.peru.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TotalBoletasEmitidasDTO {
    private String nombresApellidosUsuario;
    private Integer nroBoleta;
    private String dniPasajero;
    private LocalDate fechEmisionBoleta;
    private LocalTime horaEmisionBoleta;
    private String nombreTipoVendedor;
    private String codMoneda;
    private String detalleServicio;
    private LocalDate fechViaje;
    private LocalTime horaViaje;
    private String nroAsiento;
    private String nombreCondicion;
    private Double valorDeclaradoBoleta;
}