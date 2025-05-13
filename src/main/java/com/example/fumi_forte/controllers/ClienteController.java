package com.example.fumi_forte.controllers;

import com.example.fumi_forte.models.Usuario;
import com.example.fumi_forte.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClienteController {
    @Autowired
    private UsuarioRepository usuarios;
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@RequestBody Usuario usuario){
        if (usuarios.existsByCorreo(usuario.getCorreo())) {
            return ResponseEntity.badRequest().body("El correo ya está registrado.");
        }
        usuario.setContraseña(passwordEncoder.encode(usuario.getContraseña()));
        Usuario nuevoUsuario=usuarios.save(usuario);
        return ResponseEntity.ok(nuevoUsuario);
    }
    
}
