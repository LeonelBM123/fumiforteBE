package com.example.fumi_forte.controllers;

import com.example.fumi_forte.models.Bitacora;
import com.example.fumi_forte.models.SolicitudServicio;
import com.example.fumi_forte.models.Usuario;
import com.example.fumi_forte.repository.SolicitudServicioRepository;
import com.example.fumi_forte.repository.UsuarioRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController

public class SolicitudServicioController {

    @Autowired
    private SolicitudServicioRepository solicitudServicioRepository;
    @Autowired
    private UsuarioRepository usuarios;
    
    @PostMapping
    @RequestMapping("/solicitud_servicio")
    public SolicitudServicio registrarSolicitud(@RequestBody SolicitudServicio solicitud) {
        solicitud.setEstado("Pendiente");
        solicitud.setIdGerente(null);
        solicitud.setIdCertificado(null);;
        solicitud.setMontoPendienteCotizacion(new BigDecimal("0.00"));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
         if (auth != null && auth.isAuthenticated()) {
             String username = auth.getName();
             Usuario usuario = usuarios.findByNombreCompleto(username);
             solicitud.setIdCliente(usuario.getIdUsuario());
         }
        solicitudServicioRepository.save(solicitud);
        return solicitud;
    }
}
