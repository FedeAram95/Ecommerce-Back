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

/**
 * Esta clase sirve para DataTransfer de los distintos registros que se hacen al cambiar
 * el rol de un Usuario.
 */

@Entity
@Data
@Table(name = "cambios_rol")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CambioRol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "usuario_admin")
    private String usuarioAdmin;
    @Column(name = "usuario_cambio_rol")
    private String usuarioCambioRol;
    @Column(name = "nuevo_rol")
    private String nuevoRol;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_cambio")
    private Date fechaCambio;
}
