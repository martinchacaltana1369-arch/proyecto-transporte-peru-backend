package com.transporte.peru.service;

import com.transporte.peru.models.Estado;
import com.transporte.peru.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class EstadoService {
    
    @Autowired
    private EstadoRepository estadoRepository;
    
    public List<Estado> listarEstados() {
        return estadoRepository.findAll();
    }
    
    public boolean cambiarEstadoUsuario(Integer idUsuario, Integer estado) {
        return estadoRepository.updateEstadoUsuario(idUsuario, estado) > 0;
    }
}