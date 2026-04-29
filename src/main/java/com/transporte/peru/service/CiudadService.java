package com.transporte.peru.service;

import com.transporte.peru.dto.CiudadesMasVisitadasDTO;
import com.transporte.peru.models.Ciudad;
import com.transporte.peru.repository.CiudadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CiudadService {
    
    @Autowired
    private CiudadRepository ciudadRepository;
    
    public List<Ciudad> listarTodas() {
        return ciudadRepository.findAll();
    }
    
    public Optional<String> obtenerNombreCiudad(Integer id) {
        return ciudadRepository.findNombreCiudadById(id);
    }
    
    @Transactional
    public List<CiudadesMasVisitadasDTO> ciudadesMasVisitadas() {
        List<Object[]> resultados = ciudadRepository.ciudadesMasVisitadas();
        return resultados.stream()
            .map(obj -> new CiudadesMasVisitadasDTO(
                (Integer) obj[0],
                (String) obj[1],
                ((Number) obj[2]).intValue()
            ))
            .collect(Collectors.toList());
    }
    
    public Optional<Ciudad> obtenerPorId(Integer id) {
        return ciudadRepository.findById(id);
    }
}