package com.ecommerce.ecommerce.carrito.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ecommerce.ecommerce.catalogo.skus.entities.Sku;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "carrito_detalles")
@AllArgsConstructor
@NoArgsConstructor
public class DetalleCarrito implements Serializable {

    private final static long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sku_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Sku sku;
    private Integer cantidad;

    public Double getSubtotal() {
        // Si existe promoción vigente, el subtotal se calcula acorde al precio oferta.
        if (sku.getPromocion() != null && sku.getPromocion().getEstaVigente()) {
            return cantidad.doubleValue() * sku.getPromocion().getPrecioOferta();
        }

        return cantidad.doubleValue() * sku.getPrecio();
    }
}
