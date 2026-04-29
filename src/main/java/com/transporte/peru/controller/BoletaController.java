package com.transporte.peru.controller;

import com.transporte.peru.dto.ApiResponse;
import com.transporte.peru.dto.ListarBoletaPorIdReservaDTO;
import com.transporte.peru.models.*;
import com.transporte.peru.service.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/boletas")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class BoletaController {
    
    @Autowired
    private BoletaService boletaService;
    
    @Autowired
    private ReservaService reservaService;
    
    @Autowired
    private CondicionPagoService condicionPagoService;
    
    @GetMapping("/condiciones-pago")
    public ResponseEntity<ApiResponse> listarCondicionesPago() {
        return ResponseEntity.ok(new ApiResponse(true, "Condiciones obtenidas", condicionPagoService.listarTodas()));
    }
    
    @PostMapping("/pagar/{idReserva}")
    public ResponseEntity<ApiResponse> pagarReserva(@PathVariable Integer idReserva, 
                                                     @RequestParam String codMoneda,
                                                     @RequestParam Integer idCondicionPago) {
        return reservaService.obtenerPorId(idReserva)
                .map(reserva -> {
                    // Calcular valor
                    BigDecimal valor = reserva.getClase().getPrecio();
                    
                    // Crear boleta
                    BoletaElectronica boleta = new BoletaElectronica();
                    boleta.setPasajero(reserva.getPasajero());
                    boleta.setMoneda(new Moneda());
                    boleta.getMoneda().setCodMoneda(codMoneda);
                    boleta.setCiudadOrigen(reserva.getCiudadOrigen());
                    boleta.setCiudadDestino(reserva.getCiudadDestino());
                    boleta.setFechViaje(reserva.getFechaViaje());
                    boleta.setHoraViaje(reserva.getHoraViaje());
                    boleta.setNroAsiento(reserva.getNroAsiento());
                    boleta.setClase(reserva.getClase());
                    boleta.setCondicionPago(new CondicionPago());
                    boleta.getCondicionPago().setIdCondicionPago(idCondicionPago);
                    boleta.setTipoVendedor(new TipoVendedor());
                    boleta.getTipoVendedor().setCodTipoVendedor(1); // Vendedor Web
                    boleta.setFechEmisionBoleta(LocalDate.now());
                    boleta.setHoraEmisionBoleta(LocalTime.now());
                    boleta.setValorDeclaradoBoleta(valor);
                    
                    // Crear servicio
                    ServicioBoleta servicio = new ServicioBoleta();
                    servicio.setCantidad(1);
                    servicio.setDetalleServicio("Pasaje " + 
                        reserva.getCiudadOrigen().getNombreCiudad() + " - " + 
                        reserva.getCiudadDestino().getNombreCiudad() + " " + 
                        reserva.getClase().getTipoClase());
                    servicio.setPrecioUnitarioServicio(valor);
                    servicio.setRutaServicio(reserva.getRutaServicio());
                    servicio.setUnidadMedida(new UnidadMedida());
                    servicio.getUnidadMedida().setIdUnidadMedida(1);
                    
                    BoletaElectronica boletaGuardada = boletaService.registrarBoleta(boleta, servicio);
                    reservaService.actualizarEstado(idReserva, "PAGADA");
                    
                    return ResponseEntity.ok(new ApiResponse(true, "Pago realizado exitosamente", boletaGuardada));
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse(false, "Reserva no encontrada", null)));
    }
    
    @GetMapping("/reserva/{idReserva}")
    public ResponseEntity<ApiResponse> listarBoletasPorReserva(@PathVariable Integer idReserva) {
        List<ListarBoletaPorIdReservaDTO> boletas = boletaService.listarBoletaPorIdReserva(idReserva);
        return ResponseEntity.ok(new ApiResponse(true, "Boletas obtenidas", boletas));
    }
    
    @GetMapping("/numero/{nroBoleta}")
    public ResponseEntity<ApiResponse> listarBoletaPorNumero(@PathVariable Integer nroBoleta) {
        List<ListarBoletaPorIdReservaDTO> boletas = boletaService.listarBoletaPorNro(nroBoleta);
        return ResponseEntity.ok(new ApiResponse(true, "Boleta obtenida", boletas));
    }
    
    @GetMapping("/pdf/{nroBoleta}")
    public ResponseEntity<byte[]> generarPDF(@PathVariable Integer nroBoleta) {
        try {
            List<ListarBoletaPorIdReservaDTO> lista = boletaService.listarBoletaPorNro(nroBoleta);
            
            if (lista.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            
            // ✅ Usar el archivo .jasper YA COMPILADO
            InputStream jrxmlStream = new ClassPathResource("reports/boleta.jrxml").getInputStream();
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlStream);
            
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lista);
            
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("NRO_BOLETA", nroBoleta);
            
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource);
            byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(ContentDisposition.inline().filename("boleta_" + nroBoleta + ".pdf").build());
            
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    private BigDecimal calcularValorPorClase(Integer codClase) {
        return switch (codClase) {
            case 1 -> new BigDecimal("150.00");
            case 2 -> new BigDecimal("200.00");
            case 3 -> new BigDecimal("180.00");
            case 4 -> new BigDecimal("220.00");
            case 5 -> new BigDecimal("170.00");
            case 6 -> new BigDecimal("190.00");
            case 7 -> new BigDecimal("210.00");
            case 8 -> new BigDecimal("230.00");
            case 9 -> new BigDecimal("240.00");
            case 10 -> new BigDecimal("250.00");
            default -> BigDecimal.ZERO;
        };
    }
}