/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.fumi_forte.repository;

import com.example.fumi_forte.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author PC
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    Usuario findByCorreo(String Correo);
    Boolean existsByCorreo(String Correo);
}
