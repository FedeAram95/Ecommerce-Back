package com.ecommerce.ecommerce.catalogo.productos.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "producto_propiedades")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PropiedadProducto implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "El nombre de la propiedad del producto es obligatorio")
    private String nombre;
    /**
     * Este atributo será true si se quiere utilizar la propiedad de producto para
     * generar combinaciones. Si es false, no se usará para combinaciones, lo que implica
     * que será solo una propiedad visible, pero no será seleccionable para vender.
     */
    private boolean variable;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "producto_propiedad_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<ValorPropiedadProducto> valores;
}
