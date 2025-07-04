/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.fumi_forte.controllers;

import com.example.fumi_forte.dto.BitacoraReporteDto;
import com.example.fumi_forte.dto.CertificadoReporteDto;
import com.example.fumi_forte.dto.PagoSesionReporteDto;
import com.example.fumi_forte.dto.ProductoReporteDto;
import com.example.fumi_forte.dto.SesionReporteDto;
import com.example.fumi_forte.dto.SolicitudReporteDto;
import com.example.fumi_forte.dto.UsuarioReporteDto;
import com.example.fumi_forte.services.BitacoraReporteService;
import com.example.fumi_forte.services.CertificadoReporteService;
import com.example.fumi_forte.services.PagoCotizacionReporteService;
import com.example.fumi_forte.services.PagoSesionReporteService;
import com.example.fumi_forte.services.ProductoReporteService;
import com.example.fumi_forte.services.SesionReporteService;
import com.example.fumi_forte.services.SolicitudReporteService;
import com.example.fumi_forte.services.UsuarioReporteService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reporte")
@RequiredArgsConstructor
public class ReportesController {
    @Autowired
    private UsuarioReporteService usuarioService;

    @PostMapping("/usuario")
    public List<UsuarioReporteDto> buscarUsuarios(
        @RequestBody Map<String, String> filtros) {
        String nombre = "%"+filtros.get("nombre")+"%";
        String estado = "%"+filtros.get("estado")+"%";
        String rol = "%"+filtros.get("rol")+"%";
        return usuarioService.buscarUsuarios(nombre, estado, rol);
    }
    
    private final CertificadoReporteService certificadoService;

    @PostMapping("/certificados")
    public List<CertificadoReporteDto> buscarPorEstado(@RequestBody Map<String, String> filtros) {
        String estado = filtros.get("estado");
        return certificadoService.buscarPorEstado(estado);
    }
    
    private final BitacoraReporteService bitacoraService;

    @PostMapping("/bitacora")
    public List<BitacoraReporteDto> buscarPorAccion(@RequestBody Map<String, LocalDateTime> filtros) {
        LocalDateTime desde = filtros.get("desde");
        LocalDateTime hasta = filtros.get("hasta");
        return bitacoraService.buscarPorAccion(desde,hasta);
    }
    
    private final SolicitudReporteService solicitudService;
    @PostMapping("/Solicitud")
    public List<SolicitudReporteDto> buscarSolicitud(@RequestBody Map<String, String> filtros){
        String estado = filtros.get("estado");
        String requiere_certificado = filtros.get("requiere_certificado");
        return solicitudService.buscarSolicitud(estado, requiere_certificado);
    }
    
    private final SesionReporteService sesionService;

    @PostMapping("/sesiones")
    public List<SesionReporteDto> buscarSesiones(@RequestBody Map<String, String> filtros) {
        Long idSolicitud = Long.parseLong(filtros.get("id_solicitud"));
        String estado = filtros.getOrDefault("estado", "");
        return sesionService.buscarSesiones(idSolicitud, estado);
    }
    
    private final PagoSesionReporteService pagoSesionService;

    @PostMapping("/pago-sesion")
    public List<PagoSesionReporteDto> buscarPagos(@RequestBody Map<String, String> filtros) {
        String fechaStr = filtros.get("fecha");
        String tipoPago = filtros.get("tipo_pago");

        LocalDate fecha = null;
        if (fechaStr != null && !fechaStr.isBlank()) {
            fecha = LocalDate.parse(fechaStr);
        }

        return pagoSesionService.buscarPagosPorFiltros(fecha, tipoPago);
    }
    
    private final PagoCotizacionReporteService pagoCotizacionService;
    @PostMapping("/pago-cotizacion")
    public List<PagoSesionReporteDto> buscarPagos2(@RequestBody Map<String, String> filtros) {
        String fechaStr = filtros.get("fecha");
        String tipoPago = filtros.get("tipo_pago");
        LocalDate fecha = null;
        if (fechaStr != null && !fechaStr.isBlank()) {
            fecha = LocalDate.parse(fechaStr);
        }

        return pagoCotizacionService.buscarPagosPorFiltros2(fecha, tipoPago);
    }
    private final ProductoReporteService productoService;

    @GetMapping
    public List<ProductoReporteDto> listarProductos() {
        return productoService.obtenerTodos();
    }
}
