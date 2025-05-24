package com.example.fumi_forte.repository;

import com.example.fumi_forte.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    Usuario findByCorreo(String Correo);
    Boolean existsByCorreo(String Correo);
    Usuario findByNombreCompleto(String nombreCompleto);
}
