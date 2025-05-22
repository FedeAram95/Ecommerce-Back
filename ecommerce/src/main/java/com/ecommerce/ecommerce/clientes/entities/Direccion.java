package com.ecommerce.ecommerce.clientes.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.ecommerce.ecommerce.localizaciones.entities.Ciudad;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "direcciones")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Direccion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La ciudad es obligatoria.")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ciudad_id")
    @JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
    private Ciudad ciudad;

    @NotNull(message = "El nombre de la calle es obligatorio.")
    private String calle;

    @NotNull(message = "El número de la calle es obligatorio.")
    @Column(name = "numero_calle")
    private String numeroCalle;

    private String piso;

    @NotNull(message = "El código postal es obligatorio.")
    @Column(name = "codigo_postal")
    private String codigoPostal;
}
