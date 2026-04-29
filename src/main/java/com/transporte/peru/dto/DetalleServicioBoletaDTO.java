package com.transporte.peru.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleServicioBoletaDTO {
    private Integer nroBoleta;
    private Integer codServicioBoleta;
}