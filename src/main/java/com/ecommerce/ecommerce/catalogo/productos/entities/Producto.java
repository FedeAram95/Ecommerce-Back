package com.ecommerce.ecommerce.catalogo.productos.entities;

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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.springframework.lang.Nullable;

import com.ecommerce.ecommerce.catalogo.categorias.entities.Subcategoria;
import com.ecommerce.ecommerce.catalogo.promociones.entities.Promocion;
import com.ecommerce.ecommerce.catalogo.skus.entities.Sku;
import com.ecommerce.ecommerce.imagenes.entities.Imagen;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "productos")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Producto implements Serializable {

    private final static long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El nombre del producto es obligatorio.")
    private String nombre;

    @Nullable
    @Lob
    private String descripcion;

    @NotNull(message = "El precio del producto es obligatorio.")
    private Double precio;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "promocion_id")
    private Promocion promocion;

    @NotNull(message = "La disponibilidad inicial del producto es obligatorio.")
    @Column(name = "disponibilidad_general")
    private Integer disponibilidadGeneral;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_creacion")
    private Date fechaCreacion;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "foto_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Imagen foto;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "producto_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<Imagen> imagenes;

    private boolean activo;

    private boolean destacado;

    @NotNull(message = "La subcategoria del producto es obligatoria.")
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "subcategoria_id")
    private Subcategoria subcategoria;

    @NotNull(message = "La marca del producto es obligatoria.")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "marca_id")
    private Marca marca;

    @NotNull(message = "La unidad de medida del producto es obligatoria.")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unidad_medida_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private UnidadMedida unidadMedida;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "productos_x_propiedades",
            joinColumns = @JoinColumn(name = "producto_id"),
            inverseJoinColumns = @JoinColumn(name = "producto_propiedad_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"producto_id", "producto_propiedad_id"})})
    private List<PropiedadProducto> propiedades;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "producto_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<Caracteristica> caracteristicas;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "default_sku_id")
    @JsonIgnoreProperties(value = {"producto", "defaultProducto" , "hibernateLazyInitializer", "handler"}, allowSetters = true)
    private Sku defaultSku;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "producto", cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"producto", "defaultProducto", "hibernateLazyInitializer", "handler"}, allowSetters = true)
    private List<Sku> skus;

    public boolean isVendibleSinPropiedades() {
        return this.skus.size() == 0;
    }
}
