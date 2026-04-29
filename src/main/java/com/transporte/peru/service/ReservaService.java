package com.transporte.peru.service;

import com.transporte.peru.dto.ReservaDetalleDTO;
import com.transporte.peru.models.*;
import com.transporte.peru.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReservaService {
    
    @Autowired
    private ReservaRepository reservaRepository;
    
    @Autowired
    private PasajeroRepository pasajeroRepository;
    
    @Autowired
    private CiudadRepository ciudadRepository;
    
    @Autowired
    private RutaServicioRepository rutaServicioRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private ClaseRepository claseRepository;
    
    public boolean verificarAsientoDisponible(Reserva reserva) {
        return !reservaRepository.existsAsientoOcupado(
            reserva.getFechaViaje(),
            reserva.getHoraViaje(),
            reserva.getNroAsiento(),
            reserva.getCiudadOrigen().getIdCiudad(),
            reserva.getCiudadDestino().getIdCiudad(),
            reserva.getIdReserva() != null ? reserva.getIdReserva() : 0
        );
    }
    
    public Reserva registrarReserva(Reserva reserva, Pasajero pasajero) {
        // Verificar/guardar pasajero
        if (!pasajeroRepository.existsByDniPasajero(pasajero.getDniPasajero())) {
            pasajeroRepository.save(pasajero);
        }
        
        // Obtener o crear ruta
        String descripcionRuta = reserva.getCiudadOrigen().getNombreCiudad() + " - " + 
                                 reserva.getCiudadDestino().getNombreCiudad();
        RutaServicio ruta = rutaServicioRepository.findByDescripcionRuta(descripcionRuta)
            .orElseGet(() -> {
                RutaServicio nuevaRuta = new RutaServicio();
                nuevaRuta.setDescripcionRuta(descripcionRuta);
                return rutaServicioRepository.save(nuevaRuta);
            });
        
        reserva.setRutaServicio(ruta);
        reserva.setPasajero(pasajero);
        reserva.setEstado("PENDIENTE DE PAGO");
        
        return reservaRepository.save(reserva);
    }
    
    public Optional<Reserva> obtenerPorId(Integer id) {
        return reservaRepository.findById(id);
    }
    
    public List<Reserva> listarTodas() {
        return reservaRepository.findAll();
    }
    
    public List<Reserva> listarPorUsuario(Integer idUsuario) {
        return reservaRepository.findByUsuario_IdUsuario(idUsuario);
    }
    
    public List<ReservaDetalleDTO> listarReservasConDetalle(Integer idUsuario) {
        return reservaRepository.findByUsuario_IdUsuario(idUsuario).stream()
            .map(this::convertirADetalleDTO)
            .collect(Collectors.toList());
    }
    
    private ReservaDetalleDTO convertirADetalleDTO(Reserva reserva) {
        ReservaDetalleDTO dto = new ReservaDetalleDTO();
        dto.setIdReserva(reserva.getIdReserva());
        dto.setNombresApellidos(reserva.getPasajero().getNombrePasajero() + " " + 
                                reserva.getPasajero().getApellidosPasajero());
        dto.setCiudadOrigen(reserva.getCiudadOrigen().getNombreCiudad());
        dto.setCiudadDestino(reserva.getCiudadDestino().getNombreCiudad());
        dto.setTipoClase(reserva.getClase().getTipoClase());
        dto.setPrecio(reserva.getClase().getPrecio().doubleValue());
        dto.setFechaViaje(reserva.getFechaViaje());
        dto.setHoraViaje(reserva.getHoraViaje());
        dto.setNroAsiento(reserva.getNroAsiento());
        dto.setEstado(reserva.getEstado());
        dto.setIdUsuario(reserva.getUsuario().getIdUsuario());
        return dto;
    }
    
    public boolean actualizarEstado(Integer id, String estado) {
        return reservaRepository.updateEstado(id, estado) > 0;
    }
    
    public Reserva actualizarReserva(Reserva reserva, Pasajero pasajero) {
        // Actualizar pasajero si es necesario
        pasajeroRepository.save(pasajero);
        
        // Actualizar ruta
        String descripcionRuta = reserva.getCiudadOrigen().getNombreCiudad() + " - " + 
                                 reserva.getCiudadDestino().getNombreCiudad();
        RutaServicio ruta = rutaServicioRepository.findByDescripcionRuta(descripcionRuta)
            .orElseGet(() -> {
                RutaServicio nuevaRuta = new RutaServicio();
                nuevaRuta.setDescripcionRuta(descripcionRuta);
                return rutaServicioRepository.save(nuevaRuta);
            });
        
        reserva.setRutaServicio(ruta);
        reserva.setPasajero(pasajero);
        
        return reservaRepository.save(reserva);
    }
}