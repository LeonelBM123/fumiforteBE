package com.example.fumi_forte.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Entity
@Table(name="usuario")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Usuario {
    @Id
    @GeneratedValue
    private Long idUsuario;
    private String nombreCompleto;
    private String telefono;
    private String direccion;
    private String correo;
    private String contraseña;
    private String rol;
}
