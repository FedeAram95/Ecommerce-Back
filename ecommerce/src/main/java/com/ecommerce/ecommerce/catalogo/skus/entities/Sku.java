package com.ecommerce.ecommerce.catalogo.skus.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.springframework.lang.Nullable;

import com.ecommerce.ecommerce.catalogo.productos.entities.Producto;
import com.ecommerce.ecommerce.catalogo.productos.entities.ValorPropiedadProducto;
import com.ecommerce.ecommerce.catalogo.promociones.entities.Promocion;
import com.ecommerce.ecommerce.imagenes.entities.Imagen;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "skus")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Sku implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Nullable
    @Lob
    private String descripcion;

    @NotNull(message = "El precio del Sku es obligatorio")
    private Double precio;

    private boolean activo;

    @NotNull(message = "La disponibilidad del Sku es obligatoria")
    private Integer disponibilidad;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "promocion_id")
    private Promocion promocion;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_creacion")
    private Date fechaCreacion;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "foto_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Imagen foto;

    @Column(name = "valores_data")
    private String valoresData;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
    @JoinTable(name = "sku_x_valores_propiedades_producto",
            joinColumns = @JoinColumn(name = "sku_id"),
            inverseJoinColumns = @JoinColumn(name = "producto_valores_propiedad_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"sku_id", "producto_valores_propiedad_id"})})
    private List<ValorPropiedadProducto> valores;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "default_producto_id")
    @JsonIgnoreProperties(value = {"skus", "defaultSku" , "hibernateLazyInitializer", "handler"}, allowSetters = true)
    @ToString.Exclude
    private Producto defaultProducto;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "producto_id")
    @JsonIgnoreProperties(value = {"skus", "defaultSku" , "hibernateLazyInitializer", "handler"}, allowSetters = true)
    @ToString.Exclude
    private Producto producto;
}
