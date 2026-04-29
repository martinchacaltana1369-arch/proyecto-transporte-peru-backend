package com.transporte.peru.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "BOLETA_ELECTRONICA")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoletaElectronica {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NRO_BOLETA")
    private Integer nroBoleta;
    
    // Relaciones ManyToOne
    @ManyToOne
    @JoinColumn(name = "DNI_PASAJERO", nullable = false)
    private Pasajero pasajero;
    
    @ManyToOne
    @JoinColumn(name = "COD_TIPO_VENDEDOR", nullable = false)
    private TipoVendedor tipoVendedor;
    
    @ManyToOne
    @JoinColumn(name = "COD_MONEDA", nullable = false)
    private Moneda moneda;
    
    @ManyToOne
    @JoinColumn(name = "ID_CIUDAD_ORIGEN", nullable = false)
    private Ciudad ciudadOrigen;
    
    @ManyToOne
    @JoinColumn(name = "ID_CIUDAD_DESTINO", nullable = false)
    private Ciudad ciudadDestino;
    
    @ManyToOne
    @JoinColumn(name = "COD_CLASE", nullable = false)
    private Clase clase;
    
    @ManyToOne
    @JoinColumn(name = "ID_CONDICION_PAGO", nullable = false)
    private CondicionPago condicionPago;
    
    // Campos básicos
    @Column(name = "FECH_EMISION_BOLETA", nullable = false)
    private LocalDate fechEmisionBoleta;
    
    @Column(name = "HORA_EMISION_BOLETA", nullable = false)
    private LocalTime horaEmisionBoleta;
    
    @Column(name = "FECH_VIAJE", nullable = false)
    private LocalDate fechViaje;
    
    @Column(name = "HORA_VIAJE", nullable = false)
    private LocalTime horaViaje;
    
    @Column(name = "NRO_ASIENTO", nullable = false, length = 10)
    private String nroAsiento;
    
    @Column(name = "FECHA_VENCIMIENTO")
    private LocalDate fechaVencimiento;
    
    @Column(name = "NRO_ORDEN_COMPRA", length = 50)
    private String nroOrdenCompra;
    
    @Column(name = "VALOR_DECLARADO_BOLETA", precision = 10, scale = 2)
    private BigDecimal valorDeclaradoBoleta;
    
    // Relación ManyToMany con ServicioBoleta
    @ManyToMany
    @JoinTable(
        name = "DETALLE_SERVICIO_BOLETA",
        joinColumns = @JoinColumn(name = "NRO_BOLETA"),
        inverseJoinColumns = @JoinColumn(name = "COD_SERVICIO_BOLETA")
    )
    private List<ServicioBoleta> servicios = new ArrayList<>();
}