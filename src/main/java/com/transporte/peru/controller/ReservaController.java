package com.transporte.peru.controller;

import com.transporte.peru.dto.ApiResponse;
import com.transporte.peru.dto.ReservaDetalleDTO;
import com.transporte.peru.dto.ReservaRequest;
import com.transporte.peru.models.*;
import com.transporte.peru.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reservas")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class ReservaController {
    
    @Autowired
    private ReservaService reservaService;
    
    @Autowired
    private CiudadService ciudadService;
    
    @Autowired
    private ClaseService claseService;
    
    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping("/catalogos")
    public ResponseEntity<ApiResponse> obtenerCatalogos() {
        CatalogosResponse response = new CatalogosResponse();
        response.setCiudades(ciudadService.listarTodas());
        response.setClases(claseService.listarTodas());
        return ResponseEntity.ok(new ApiResponse(true, "Catálogos obtenidos", response));
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse> crearReserva(@RequestBody ReservaRequest request) {
        // Validar hora
        LocalTime hora = request.getHoraViaje();
        if (hora.isBefore(LocalTime.of(5, 0)) || hora.isAfter(LocalTime.of(23, 0))) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "La hora debe estar entre 5:00 AM y 11:00 PM", null));
        }
        
        // Validar ciudades diferentes
        if (request.getIdCiudadOrigen().equals(request.getIdCiudadDestino())) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "Origen y destino no pueden ser iguales", null));
        }
        
        // Crear reserva
        Reserva reserva = new Reserva();
        reserva.setUsuario(new Usuario());
        reserva.getUsuario().setIdUsuario(request.getIdUsuario());
        reserva.setCiudadOrigen(new Ciudad());
        reserva.getCiudadOrigen().setIdCiudad(request.getIdCiudadOrigen());
        reserva.setCiudadDestino(new Ciudad());
        reserva.getCiudadDestino().setIdCiudad(request.getIdCiudadDestino());
        reserva.setClase(new Clase());
        reserva.getClase().setCodClase(request.getCodClase());
        reserva.setFechaViaje(request.getFechaViaje());
        reserva.setHoraViaje(request.getHoraViaje());
        reserva.setNroAsiento(request.getNroAsiento());
        
        // Validar asiento disponible
        if (!reservaService.verificarAsientoDisponible(reserva)) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "El asiento ya está reservado", null));
        }
        
        // Crear pasajero
        Pasajero pasajero = new Pasajero();
        pasajero.setDniPasajero(request.getDniPasajero());
        pasajero.setNombrePasajero(request.getNombrePasajero());
        pasajero.setApellidosPasajero(request.getApellidosPasajero());
        pasajero.setDireccionPasajero(request.getDireccionPasajero());
        pasajero.setTelefonoPasajero(request.getTelefonoPasajero());
        
        Reserva nuevaReserva = reservaService.registrarReserva(reserva, pasajero);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(true, "Reserva creada exitosamente", nuevaReserva));
    }
    
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<ApiResponse> listarReservasPorUsuario(@PathVariable Integer idUsuario) {
        List<ReservaDetalleDTO> reservas = reservaService.listarReservasConDetalle(idUsuario);
        return ResponseEntity.ok(new ApiResponse(true, "Reservas obtenidas", reservas));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> obtenerReserva(@PathVariable Integer id) {
        return reservaService.obtenerPorId(id)
                .map(reserva -> ResponseEntity.ok(new ApiResponse(true, "Reserva encontrada", reserva)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse(false, "Reserva no encontrada", null)));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> actualizarReserva(@PathVariable Integer id, @RequestBody ReservaRequest request) {
        // Validaciones similares a crear
        if (request.getIdCiudadOrigen().equals(request.getIdCiudadDestino())) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "Origen y destino no pueden ser iguales", null));
        }
        Reserva reservaExistente = reservaService.obtenerPorId(id).orElse(null);
        
        Pasajero pasajero = new Pasajero();
        pasajero.setDniPasajero(request.getDniPasajero());
        pasajero.setNombrePasajero(request.getNombrePasajero());
        pasajero.setApellidosPasajero(request.getApellidosPasajero());
        pasajero.setDireccionPasajero(request.getDireccionPasajero());
        pasajero.setTelefonoPasajero(request.getTelefonoPasajero());
        
        if (reservaExistente != null) {
        	reservaExistente.setPasajero(pasajero);
        	reservaExistente.setCiudadOrigen(new Ciudad());
        	reservaExistente.getCiudadOrigen().setIdCiudad(request.getIdCiudadOrigen());
        	reservaExistente.setCiudadDestino(new Ciudad());
        	reservaExistente.getCiudadDestino().setIdCiudad(request.getIdCiudadDestino());
        	reservaExistente.setClase(new Clase());
        	reservaExistente.getClase().setCodClase(request.getCodClase());
        	reservaExistente.setFechaViaje(request.getFechaViaje());
        	reservaExistente.setHoraViaje(request.getHoraViaje());
        	reservaExistente.setNroAsiento(request.getNroAsiento());
        }
        /*
        Reserva reserva = new Reserva();
        reserva.setIdReserva(id);
        reserva.setCiudadOrigen(new Ciudad());
        reserva.getCiudadOrigen().setIdCiudad(request.getIdCiudadOrigen());
        reserva.setCiudadDestino(new Ciudad());
        reserva.getCiudadDestino().setIdCiudad(request.getIdCiudadDestino());
        reserva.setClase(new Clase());
        reserva.getClase().setCodClase(request.getCodClase());
        reserva.setFechaViaje(request.getFechaViaje());
        reserva.setHoraViaje(request.getHoraViaje());
        reserva.setNroAsiento(request.getNroAsiento());
        */
        if (!reservaService.verificarAsientoDisponible(reservaExistente)) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "El asiento ya está reservado", null));
        }
        
        Reserva actualizada = reservaService.actualizarReserva(reservaExistente, pasajero);
        return ResponseEntity.ok(new ApiResponse(true, "Reserva actualizada", actualizada));
    }
    
    @PatchMapping("/{id}/estado")
    public ResponseEntity<ApiResponse> actualizarEstado(@PathVariable Integer id, @RequestParam String estado) {
        boolean actualizado = reservaService.actualizarEstado(id, estado);
        if (actualizado) {
            return ResponseEntity.ok(new ApiResponse(true, "Estado actualizado", null));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse(false, "Reserva no encontrada", null));
    }
    
    // Clase interna para respuesta de catálogos
    static class CatalogosResponse {
        private List<Ciudad> ciudades;
        private List<Clase> clases;
        
        public List<Ciudad> getCiudades() { return ciudades; }
        public void setCiudades(List<Ciudad> ciudades) { this.ciudades = ciudades; }
        public List<Clase> getClases() { return clases; }
        public void setClases(List<Clase> clases) { this.clases = clases; }
    }
}