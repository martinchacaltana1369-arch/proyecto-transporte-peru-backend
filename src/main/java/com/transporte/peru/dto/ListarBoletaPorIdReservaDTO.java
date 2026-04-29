package com.transporte.peru.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListarBoletaPorIdReservaDTO {
    private Integer idReserva;
    private Integer nroBoleta;
    private Integer codServicioBoleta;
    private Integer cantidad;
    private String dniPasajero;
    private String nombresApellidosPasajero;
    private LocalDate fechEmisionBoleta;
    private LocalTime horaEmisionBoleta;
    private String nroAsiento;
    private LocalDate fechViaje;
    private LocalTime horaViaje;
    private String nombreTipoVendedor;
    private String nombreCondicion;
    private String detalleServicio;
    private Double precioUnitarioServicio;
    private Double valorDeclaradoBoleta;
}