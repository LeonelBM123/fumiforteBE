package com.example.fumi_forte.controllers;

import com.example.fumi_forte.models.Usuario;
import com.example.fumi_forte.repository.UsuarioRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClienteController {

    //CREAR
    @Autowired
    private UsuarioRepository usuarios;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@RequestBody Usuario usuario) {
        if (usuarios.existsByCorreo(usuario.getCorreo())) {
            return ResponseEntity.badRequest().body("El correo ya está registrado.");
        }
        usuario.setContraseña(passwordEncoder.encode(usuario.getContraseña()));
        Usuario nuevoUsuario = usuarios.save(usuario);
        return ResponseEntity.ok(nuevoUsuario);
    }
    
    //ELIMINAR
    @DeleteMapping("/usuarios/{id}")
    @PreAuthorize("hasAuthority('Gerente')")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) {
        if (!usuarios.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        usuarios.deleteById(id);
        return ResponseEntity.ok("Usuario eliminado correctamente.");
    }
    
    //MODIFICAR
    @PutMapping("/usuarios/{id}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioActualizado) {
        Optional<Usuario> usuarioOptional = usuarios.findById(id);

        if (!usuarioOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Usuario usuarioExistente = usuarioOptional.get();

        // Actualizar los campos necesarios (excepto contraseña si no se quiere cambiar)
        usuarioExistente.setNombreCompleto(usuarioActualizado.getNombreCompleto());
        usuarioExistente.setCorreo(usuarioActualizado.getCorreo());
        usuarioExistente.setTelefono(usuarioActualizado.getTelefono());
        usuarioExistente.setDireccion(usuarioActualizado.getDireccion());
        usuarioExistente.setContraseña(usuarioActualizado.getContraseña());
        usuarioExistente.setRol(usuarioActualizado.getRol());

        // Si se quiere actualizar la contraseña, se debe volver a codificar
        if (usuarioActualizado.getContraseña() != null && !usuarioActualizado.getContraseña().isEmpty()) {
            usuarioExistente.setContraseña(passwordEncoder.encode(usuarioActualizado.getContraseña()));
        }
        Usuario usuarioModificado = usuarios.save(usuarioExistente);
        return ResponseEntity.ok(usuarioModificado);
    }
    
    
}
