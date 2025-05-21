/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.fumi_forte.models;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "solicitud_servicio")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SolicitudServicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_solicitud_servicio")
    private Long idSolicitudServicio;

    @Column(name = "descripcion", nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "ubicacion_gps", nullable = false, length = 50)
    private String ubicacionGps;

    @Column(name = "direccion_escrita", nullable = false, columnDefinition = "TEXT")
    private String direccionEscrita;

    @Column(name = "estado", nullable = false, length = 10)
    private String estado;

    @Column(name = "monto_pendiente_cotizacion", precision = 5, scale = 2)
    private Double montoPendienteCotizacion;

    @Column(name = "cantidad_sesiones", nullable = false)
    private Short cantidadSesiones = 0;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false, foreignKey = @ForeignKey(name = "fk_solicitud_cliente"))
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(
        name = "id_gerente",
        nullable = true,
        foreignKey = @ForeignKey(name = "fk_solicitud_gerente",
                                 foreignKeyDefinition = "FOREIGN KEY (id_gerente) REFERENCES gerente(id_gerente) ON UPDATE CASCADE ON DELETE SET NULL"))
    private Gerente gerente;

    @OneToOne
    @JoinColumn(name = "id_certificado", unique = true,
        foreignKey = @ForeignKey(name = "fk_solicitud_certificado", 
                                 foreignKeyDefinition = "FOREIGN KEY (id_certificado) REFERENCES certificado_fumigacion(id_certificado) ON UPDATE CASCADE ON DELETE SET NULL"))
    private CertificadoFumigacion certificado;
}