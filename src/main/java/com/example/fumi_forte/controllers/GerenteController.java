/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.fumi_forte.controllers;

import com.example.fumi_forte.models.Bitacora;
import com.example.fumi_forte.models.Plaga;
import com.example.fumi_forte.models.Proveedor;
import com.example.fumi_forte.models.Usuario;
import com.example.fumi_forte.repository.BitacoraRepository;
import com.example.fumi_forte.repository.PlagaRepository;
import com.example.fumi_forte.repository.ProveedorRepository;
import com.example.fumi_forte.repository.UsuarioRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gerente")
@RequiredArgsConstructor
public class GerenteController {
    @Autowired
    UsuarioRepository Usuarios;
    @Autowired
    PlagaRepository Plagas;
    @Autowired
    ProveedorRepository Proveedores;
    @Autowired
    BitacoraRepository Bitacoras;
    
  
    @GetMapping("/usuarios")
    @PreAuthorize("hasAuthority('Gerente')")
    public List<Usuario> obtenerUsuarios(){
        return Usuarios.findAll();
    }
    
    @GetMapping("/plagas")
    @PreAuthorize("hasAuthority('Gerente')")
    public List<Plaga> obtenerPlaga(){
        return Plagas.findAll();
    }
    
    @GetMapping("/proveedores")
    @PreAuthorize("hasAuthority('Gerente')")
    public List<Proveedor> obtenerProveedores(){
        return Proveedores.findAll();
    }
    
    @GetMapping("/bitacoras")
    @PreAuthorize("hasAuthority('Gerente')")
    public List<Bitacora> obtenerBitacoras(){
        return Bitacoras.findAll();
    }
}

