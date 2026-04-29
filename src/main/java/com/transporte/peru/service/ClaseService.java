package com.transporte.peru.service;

import com.transporte.peru.models.Clase;
import com.transporte.peru.repository.ClaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ClaseService {
    
    @Autowired
    private ClaseRepository claseRepository;
    
    public List<Clase> listarTodas() {
        return claseRepository.findAll();
    }
    
    public Optional<String> obtenerTipoClase(Integer id) {
        return claseRepository.findTipoClaseById(id);
    }
}