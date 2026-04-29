package com.transporte.peru.service;

import com.transporte.peru.dto.RutasMasVendidasDTO;
import com.transporte.peru.models.RutaServicio;
import com.transporte.peru.repository.RutaServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class RutaServicioService {
    
    @Autowired
    private RutaServicioRepository rutaServicioRepository;
    
    public Integer obtenerOCrearRuta(Integer idOrigen, Integer idDestino, String descripcion) {
        Optional<RutaServicio> rutaExistente = rutaServicioRepository.findByDescripcionRuta(descripcion);
        if (rutaExistente.isPresent()) {
            return rutaExistente.get().getNroRutaServicio();
        }
        RutaServicio nuevaRuta = new RutaServicio();
        nuevaRuta.setDescripcionRuta(descripcion);
        return rutaServicioRepository.save(nuevaRuta).getNroRutaServicio();
    }
    
    @Transactional
    public List<RutasMasVendidasDTO> rutasMasVendidas(LocalDate fecha) {
        List<Object[]> resultados = rutaServicioRepository.rutasMasVendidas(fecha);
        return resultados.stream()
            .map(obj -> new RutasMasVendidasDTO(
                (String) obj[0],
                ((Number) obj[1]).intValue(),
                ((Number) obj[2]).doubleValue()
            ))
            .collect(Collectors.toList());
    }
}