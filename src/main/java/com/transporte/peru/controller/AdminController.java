package com.transporte.peru.controller;

import com.transporte.peru.dto.*;
import com.transporte.peru.models.Estado;
import com.transporte.peru.models.TipoUsuario;
import com.transporte.peru.models.Usuario;
import com.transporte.peru.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class AdminController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private EstadoService estadoService;
    
    @Autowired
    private CiudadService ciudadService;
    
    @Autowired
    private RutaServicioService rutaServicioService;
    
    @Autowired
    private BoletaService boletaService;
    
    @Autowired
    private TipoUsuarioService tipoUsuarioService;
    
    @GetMapping("/usuarios")
    public ResponseEntity<ApiResponse> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listarTodos();
        usuarios.forEach(u -> u.setContrasena(null));
        return ResponseEntity.ok(new ApiResponse(true, "Usuarios obtenidos", usuarios));
    }
    
    @PatchMapping("/usuarios/{id}/estado")
    public ResponseEntity<ApiResponse> cambiarEstadoUsuario(@PathVariable Integer id, @RequestParam Integer estado) {
        boolean actualizado = estadoService.cambiarEstadoUsuario(id, estado);
        if (actualizado) {
            return ResponseEntity.ok(new ApiResponse(true, "Estado actualizado", null));
        }
        return ResponseEntity.badRequest().body(new ApiResponse(false, "Error al actualizar estado", null));
    }
    
    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse> obtenerDashboard(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio) {
        
        DashboardResponse response = new DashboardResponse();
        response.setUsuarios(usuarioService.listarTodos());
        response.setTiposUsuario(tipoUsuarioService.listarTodas());
        response.setEstados(estadoService.listarEstados());
        response.setCiudadesMasVisitadas(ciudadService.ciudadesMasVisitadas());
        response.setBoletasEmitidas(boletaService.totalBoletasEmitidas());
        
        if (fechaInicio != null) {
            response.setRutasMasVendidas(rutaServicioService.rutasMasVendidas(fechaInicio));
        }
        
        return ResponseEntity.ok(new ApiResponse(true, "Dashboard obtenido", response));
    }
    
    static class DashboardResponse {
        private List<Usuario> usuarios;
        private List<TipoUsuario> tiposUsuario;
        private List<Estado> estados;
        private List<CiudadesMasVisitadasDTO> ciudadesMasVisitadas;
        private List<RutasMasVendidasDTO> rutasMasVendidas;
        private List<TotalBoletasEmitidasDTO> boletasEmitidas;
        
        // Getters y Setters
        public List<Usuario> getUsuarios() { return usuarios; }
        public void setUsuarios(List<Usuario> usuarios) { this.usuarios = usuarios; }
        public List<TipoUsuario> getTiposUsuario() { return tiposUsuario; }
        public void setTiposUsuario(List<TipoUsuario> tiposUsuario) { this.tiposUsuario = tiposUsuario; }
        public List<Estado> getEstados() { return estados; }
        public void setEstados(List<Estado> estados) { this.estados = estados; }
        public List<CiudadesMasVisitadasDTO> getCiudadesMasVisitadas() { return ciudadesMasVisitadas; }
        public void setCiudadesMasVisitadas(List<CiudadesMasVisitadasDTO> ciudadesMasVisitadas) { this.ciudadesMasVisitadas = ciudadesMasVisitadas; }
        public List<RutasMasVendidasDTO> getRutasMasVendidas() { return rutasMasVendidas; }
        public void setRutasMasVendidas(List<RutasMasVendidasDTO> rutasMasVendidas) { this.rutasMasVendidas = rutasMasVendidas; }
        public List<TotalBoletasEmitidasDTO> getBoletasEmitidas() { return boletasEmitidas; }
        public void setBoletasEmitidas(List<TotalBoletasEmitidasDTO> boletasEmitidas) { this.boletasEmitidas = boletasEmitidas; }
    }
}