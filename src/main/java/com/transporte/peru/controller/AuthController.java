package com.transporte.peru.controller;

import com.transporte.peru.dto.LoginRequest;
import com.transporte.peru.dto.ApiResponse;
import com.transporte.peru.models.Usuario;
import com.transporte.peru.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class AuthController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        Optional<Usuario> usuarioOpt = usuarioService.validar(loginRequest.getCorreo(), loginRequest.getContrasena());
        
        if (usuarioOpt.isPresent() && usuarioOpt.get().getEstado() == 1) {
            Usuario usuario = usuarioOpt.get();
            session.setAttribute("usuario", usuario);
            
            // No enviar contraseña en la respuesta
            usuario.setContrasena(null);
            
            return ResponseEntity.ok(new ApiResponse(true, "Login exitoso", usuario));
        }
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiResponse(false, "Credenciales inválidas", null));
    }
    
    @PostMapping("/registro")
    public ResponseEntity<ApiResponse> registrar(@RequestBody Usuario usuario) {
        if (usuarioService.existeCorreo(usuario.getCorreo())) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "El correo ya está registrado", null));
        }
        
        usuario.setTipo(1); // Cliente por defecto
        usuario.setEstado(1); // Activo por defecto
        
        Usuario nuevoUsuario = usuarioService.registrar(usuario);
        nuevoUsuario.setContrasena(null);
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(true, "Usuario registrado exitosamente", nuevoUsuario));
    }
    
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok(new ApiResponse(true, "Sesión cerrada", null));
    }
    
    @GetMapping("/usuario/{id}")
    public ResponseEntity<ApiResponse> obtenerUsuario(@PathVariable Integer id, HttpSession session) {
        Usuario usuarioSesion = (Usuario) session.getAttribute("usuario");
        if (usuarioSesion == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(false, "No autorizado", null));
        }
        
        Optional<Usuario> usuarioOpt = usuarioService.obtenerPorId(id);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuario.setContrasena(null);
            return ResponseEntity.ok(new ApiResponse(true, "Usuario encontrado", usuario));
        }
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse(false, "Usuario no encontrado", null));
    }
    
    @PutMapping("/usuario/{id}")
    public ResponseEntity<ApiResponse> actualizarUsuario(@PathVariable Integer id, @RequestBody Usuario usuario) {
        usuario.setIdUsuario(id);
        Usuario actualizado = usuarioService.actualizar(usuario);
        actualizado.setContrasena(null);
        return ResponseEntity.ok(new ApiResponse(true, "Usuario actualizado", actualizado));
    }
}