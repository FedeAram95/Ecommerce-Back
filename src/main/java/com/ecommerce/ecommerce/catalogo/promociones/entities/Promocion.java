package com.ecommerce.ecommerce.catalogo.promociones.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "promociones")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Promocion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "La fecha desde de la promoción es obligatoria")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_desde")
    private Date fechaDesde;
    @NotNull(message = "La fecha hasta de la promoción es obligatoria")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_hasta")
    private Date fechaHasta;
    @Column(name = "precio_oferta")
    private Double precioOferta;
    private Double porcentaje;

    public boolean getEstaVigente() {
        Date actualDate = new Date();
        return actualDate.after(this.fechaDesde) && actualDate.before(this.fechaHasta);
    }
}
