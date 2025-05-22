package com.example.fumi_forte.controllers;

import com.example.fumi_forte.models.Usuario;
import com.example.fumi_forte.repository.UsuarioRepository;
import com.example.fumi_forte.models.Cliente;
import com.example.fumi_forte.repository.ClienteRepository;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpStatus;

@RestController
public class ClienteController {

    //CREAR
    @Autowired
    private UsuarioRepository usuarios;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@RequestBody Usuario usuario) {
    try {
        // Verificar si el correo ya está registrado
        if (usuarios.existsByCorreo(usuario.getCorreo())) {
            return ResponseEntity.badRequest().body("El correo ya está registrado.");
        }

        // Encriptar la contraseña
        usuario.setContraseña(passwordEncoder.encode(usuario.getContraseña()));

        // Guardar el nuevo usuario
        Usuario nuevoUsuario = usuarios.save(usuario);

        // Retornar el usuario guardado (incluye su ID)
        return ResponseEntity.ok(nuevoUsuario);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al registrar usuario: " + e.getMessage());
        }
    }
    
    //Verificar si existe el id usuario en la tabla cliente
    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/clientes/usuario/{idUsuario}")
    public ResponseEntity<?> buscarClientePorIdUsuario(@PathVariable Long idUsuario) {
        Optional<Cliente> clienteOpt = clienteRepository.findByUsuario_IdUsuario(idUsuario);

        if (clienteOpt.isPresent()) {
            return ResponseEntity.ok(clienteOpt.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("No existe cliente para el usuario con id: " + idUsuario);
        }
    }
    
    //Registrar usuario en la tabla cliente
    @PostMapping("/registro_cliente")
    public ResponseEntity<?> registrarCliente(@RequestBody Cliente clienteRequest) {
        try {
            Optional<Usuario> usuarioOpt = usuarios.findById(clienteRequest.getIdCliente());

            if (usuarioOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
            }

            Usuario usuario = usuarioOpt.get();

            Cliente nuevoCliente = new Cliente();
            nuevoCliente.setTipoCliente(clienteRequest.getTipoCliente());
            nuevoCliente.setRazonSocial(clienteRequest.getRazonSocial());
            nuevoCliente.setNit(clienteRequest.getNit());
            nuevoCliente.setUsuario(usuario); // Esto es CLAVE para @MapsId

            clienteRepository.save(nuevoCliente);

            return ResponseEntity.ok("Cliente registrado correctamente");
        } catch (Exception e) { 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error al registrar cliente: " + e.getMessage());
        }
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
    
    @GetMapping("/clientes")
    public ResponseEntity<?> listarClientes() {
        return ResponseEntity.ok(clienteRepository.findAll());
        }
}
