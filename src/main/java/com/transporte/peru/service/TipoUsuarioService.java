package com.transporte.peru.service;

import com.transporte.peru.models.TipoUsuario;
import com.transporte.peru.repository.TipoUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TipoUsuarioService {
    
    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;
    
    public List<TipoUsuario> listarTodas() {
        return tipoUsuarioRepository.findAll();
    }
}