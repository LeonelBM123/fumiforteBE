package com.example.fumi_forte.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "trabajador")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trabajador {
    @Id
    @Column(name = "id_trabajador")
    private Long idTrabajador; 

    @Column(name = "especialidad", nullable = false, length = 50)
    private String tipoCliente;

    @Column(name = "estado", length = 10)
    private String razonSocial;

    @Column(name = "nit", length = 50)
    private String nit;

    @OneToOne
    @MapsId 
    @JoinColumn(name = "id_cliente") 
    private Usuario usuario;
}
