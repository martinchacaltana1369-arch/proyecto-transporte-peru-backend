package com.transporte.peru.service;

import com.transporte.peru.dto.ListarBoletaPorIdReservaDTO;
import com.transporte.peru.dto.TotalBoletasEmitidasDTO;
import com.transporte.peru.models.BoletaElectronica;
import com.transporte.peru.models.ServicioBoleta;
import com.transporte.peru.repository.BoletaRepository;
import com.transporte.peru.repository.ServicioBoletaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Time;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BoletaService {
    
    @Autowired
    private BoletaRepository boletaRepository;
    
    @Autowired
    private ServicioBoletaRepository servicioBoletaRepository;
    
    public BoletaElectronica registrarBoleta(BoletaElectronica boleta, ServicioBoleta servicio) {
        ServicioBoleta servicioGuardado = servicioBoletaRepository.save(servicio);
        boleta.getServicios().add(servicioGuardado);
        return boletaRepository.save(boleta);
    }
    
    @Transactional 
    public List<ListarBoletaPorIdReservaDTO> listarBoletaPorIdReserva(Integer idReserva) {
        List<Object[]> resultados = boletaRepository.listarBoletaPorIdReserva(idReserva);
        return resultados.stream()
            .map(this::convertirAListarBoletaDTO)
            .collect(Collectors.toList());
    }
    
    @Transactional
    public List<ListarBoletaPorIdReservaDTO> listarBoletaPorNro(Integer nroBoleta) {
        List<Object[]> resultados = boletaRepository.listarBoletaPorNro(nroBoleta);
        return resultados.stream()
            .map(this::convertirAListarBoletaDTO)
            .collect(Collectors.toList());
    }
    
    @Transactional 
    public List<TotalBoletasEmitidasDTO> totalBoletasEmitidas() {
        List<Object[]> resultados = boletaRepository.totalBoletasEmitidas();
        return resultados.stream()
            .map(obj -> {
                TotalBoletasEmitidasDTO dto = new TotalBoletasEmitidasDTO();
                dto.setNombresApellidosUsuario((String) obj[0]);
                dto.setNroBoleta((Integer) obj[1]);
                dto.setDniPasajero((String) obj[2]);
                dto.setFechEmisionBoleta(((java.sql.Date) obj[3]).toLocalDate());
                dto.setHoraEmisionBoleta(obj[4] instanceof Time ? ((Time) obj[4]).toLocalTime() : (LocalTime) obj[4]);
                dto.setNombreTipoVendedor((String) obj[5]);
                dto.setCodMoneda((String) obj[6]);
                dto.setDetalleServicio((String) obj[7]);
                dto.setFechViaje(((java.sql.Date) obj[8]).toLocalDate());
                dto.setHoraViaje(obj[9] instanceof Time ? ((Time) obj[9]).toLocalTime() : (LocalTime) obj[9]);
                dto.setNroAsiento((String) obj[10]);
                dto.setNombreCondicion((String) obj[11]);
                dto.setValorDeclaradoBoleta(((Number) obj[12]).doubleValue());
                return dto;
            })
            .collect(Collectors.toList());
    }
    
    private ListarBoletaPorIdReservaDTO convertirAListarBoletaDTO(Object[] obj) {
        ListarBoletaPorIdReservaDTO dto = new ListarBoletaPorIdReservaDTO();
        dto.setIdReserva((Integer) obj[0]);
        dto.setNroBoleta((Integer) obj[1]);
        dto.setCodServicioBoleta((Integer) obj[2]);
        dto.setCantidad((Integer) obj[3]);
        dto.setDniPasajero((String) obj[4]);
        dto.setNombresApellidosPasajero((String) obj[5]);
        dto.setFechEmisionBoleta(((java.sql.Date) obj[6]).toLocalDate());
        dto.setHoraEmisionBoleta(obj[7] instanceof Time ? ((Time) obj[7]).toLocalTime() : (LocalTime) obj[7]);
        dto.setNroAsiento((String) obj[8]);
        dto.setFechViaje(((java.sql.Date) obj[9]).toLocalDate());
        dto.setHoraViaje(obj[10] instanceof Time ? ((Time) obj[10]).toLocalTime() : (LocalTime) obj[10]);
        dto.setNombreTipoVendedor((String) obj[11]);
        dto.setNombreCondicion((String) obj[12]);
        dto.setDetalleServicio((String) obj[13]);
        dto.setPrecioUnitarioServicio(((Number) obj[14]).doubleValue());
        dto.setValorDeclaradoBoleta(((Number) obj[15]).doubleValue());
        return dto;
    }
}