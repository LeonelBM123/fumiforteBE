package com.example.fumi_forte.controllers;

import com.example.fumi_forte.models.SolicitudServicio;
import com.example.fumi_forte.repository.SolicitudServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController

public class SolicitudServicioController {

    @Autowired
    private SolicitudServicioRepository solicitudServicioRepository;

    @PostMapping
    @RequestMapping("/solicitud_servicio")
    public SolicitudServicio registrarSolicitud(@RequestBody SolicitudServicio solicitud) {
        return solicitudServicioRepository.save(solicitud);
    }
}
