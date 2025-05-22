package com.ecommerce.ecommerce.administracionusuarios.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "cambios_habilitacion_usuarios")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CambioHabilitacionUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "usuario_admin")
    private String usuarioAdmin;
    @Column(name = "usuario_cambio_habilitacion")
    private String usuarioCambioHabilitacion;
    private String accion;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_cambio")
    private Date fechaCambio;
}
