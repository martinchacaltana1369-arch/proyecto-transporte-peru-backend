package com.transporte.peru.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "USUARIO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USUARIO")
    private Integer idUsuario;
    
    @Column(name = "NOMBRE", nullable = false, length = 20)
    private String nombre;
    
    @Column(name = "APELLIDO", nullable = false, length = 50)
    private String apellido;
    
    @Column(name = "CORREO", nullable = false, unique = true, length = 100)
    private String correo;
    
    @Column(name = "FECHA_NAC")
    private LocalDate fechaNac;
    
    @Column(name = "CONTRASENA", nullable = false, length = 50)
    private String contrasena;
    
    @Column(name = "TIPO", nullable = false)
    private Integer tipo = 1;

    @Column(name = "ESTADO", nullable = false)
    private Integer estado = 1;
    
    // Relaciones
    /*
    @ManyToOne
    @JoinColumn(name = "TIPO", nullable = false)
    private TipoUsuario tipo;

    @ManyToOne
    @JoinColumn(name = "ESTADO", nullable = false)
    private Estado estado;
    */
    @JsonIgnore
    @OneToMany(mappedBy = "usuario")
    private List<Reserva> reservas;
    
    // Métodos helper para seguridad
    public String getNombreCompleto() {
        return this.nombre + " " + this.apellido;
    }
    
    public boolean isActivo() {
        return this.estado == 1;
    }
    
    public boolean isAdmin() {
        return this.tipo == 3;
    }
    
    public boolean isVendedor() {
        return this.tipo == 2;
    }
    
    public boolean isCliente() {
        return this.tipo == 1;
    }
}