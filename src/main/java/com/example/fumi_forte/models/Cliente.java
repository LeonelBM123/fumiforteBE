package com.example.fumi_forte.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cliente")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Long idCliente;

    @Column(name = "tipo_cliente", nullable = false, length = 10)
    private String tipoCliente;

    @Column(name = "razon_social", length = 50)
    private String razonSocial;

    @Column(name = "nit", length = 50)
    private String nit;

    @OneToOne
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_usuario", insertable = false, updatable = false)
    private Usuario usuario;
}
