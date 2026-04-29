package com.transporte.peru.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "RESERVA")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reserva {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_RESERVA")
    private Integer idReserva;
    
    // Relaciones ManyToOne
    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private Usuario usuario;
    
    @ManyToOne
    @JoinColumn(name = "DNI_PASAJERO", nullable = false)
    private Pasajero pasajero;
    
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
    @JoinColumn(name = "NRO_RUTA_SERVICIO", nullable = false)
    private RutaServicio rutaServicio;
    
    // Campos básicos
    @Column(name = "FECHA_VIAJE", nullable = false)
    private LocalDate fechaViaje;
    
    @Column(name = "HORA_VIAJE", nullable = false)
    private LocalTime horaViaje;
    
    @Column(name = "NRO_ASIENTO", nullable = false, length = 10)
    private String nroAsiento;
    
    @Column(name = "ESTADO", length = 20)
    private String estado = "PENDIENTE DE PAGO";
    
    @Column(name = "FECHA_RESERVA")
    private LocalDateTime fechaReserva = LocalDateTime.now();
    
    // Métodos helper
    public boolean isPendientePago() {
        return "PENDIENTE DE PAGO".equals(this.estado);
    }
    
    public boolean isPagada() {
        return "PAGADA".equals(this.estado);
    }
    
    public boolean isAnulada() {
        return "ANULADA".equals(this.estado);
    }
}