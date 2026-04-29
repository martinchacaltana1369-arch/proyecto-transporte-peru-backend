package com.transporte.peru.service;

import com.transporte.peru.models.CondicionPago;
import com.transporte.peru.repository.CondicionPagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CondicionPagoService {
    
    @Autowired
    private CondicionPagoRepository condicionPagoRepository;
    
    public List<CondicionPago> listarTodas() {
        return condicionPagoRepository.findAll();
    }
}