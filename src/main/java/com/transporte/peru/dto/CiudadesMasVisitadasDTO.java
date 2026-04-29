package com.transporte.peru.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CiudadesMasVisitadasDTO {
    private Integer idCiudad;
    private String nombreCiudad;
    private Integer cantVisitas;
}