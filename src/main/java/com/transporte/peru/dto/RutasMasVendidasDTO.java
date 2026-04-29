package com.transporte.peru.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RutasMasVendidasDTO {
    private String descripcionRuta;
    private Integer cantVentas;
    private Double dineroTotal;
}