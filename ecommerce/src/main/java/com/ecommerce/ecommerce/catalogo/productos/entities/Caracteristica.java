package com.ecommerce.ecommerce.catalogo.productos.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "producto_caracteristicas")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Caracteristica implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "El nombre de la característica es obligatorio")
    private String nombre;
    @NotNull(message = "El valor de la característica es obligatorio")
    private String valor;
}
