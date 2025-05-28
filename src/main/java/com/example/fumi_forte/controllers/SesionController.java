package com.example.fumi_forte.controllers;

import com.example.fumi_forte.models.Sesion;
import com.example.fumi_forte.repository.SesionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sesion")
@RequiredArgsConstructor
public class SesionController {

    private final SesionRepository sesionRepository;

    // GET: Listar todas las sesiones
    @GetMapping("/listar")
    public List<Sesion> listarSesiones() {
        return sesionRepository.findAll();
    }

    // GET: Obtener sesión por ID
    @GetMapping("/{id}")
    public ResponseEntity<Sesion> obtenerSesion(@PathVariable Long id) {
        Optional<Sesion> sesion = sesionRepository.findById(id);
        return sesion.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    // POST: Crear nueva sesión
    @PostMapping("/crear")
    public Sesion crearSesion(@RequestBody Sesion sesion) {
        return sesionRepository.save(sesion);
    }

    // PUT: Actualizar una sesión
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Sesion> actualizarSesion(@PathVariable Long id, @RequestBody Sesion sesionActualizada) {
        Optional<Sesion> sesionOptional = sesionRepository.findById(id);
        if (sesionOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Sesion sesionExistente = sesionOptional.get();
        sesionExistente.setFecha(sesionActualizada.getFecha());
        sesionExistente.setHora(sesionActualizada.getHora());
        sesionExistente.setMontoPendienteSesion(sesionActualizada.getMontoPendienteSesion());
        sesionExistente.setEstado(sesionActualizada.getEstado());
        sesionExistente.setNroSesion(sesionActualizada.getNroSesion());
        sesionExistente.setSolicitudServicio(sesionActualizada.getSolicitudServicio());

        return ResponseEntity.ok(sesionRepository.save(sesionExistente));
    }

    // DELETE: Eliminar sesión
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarSesion(@PathVariable Long id) {
        if (!sesionRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        sesionRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
