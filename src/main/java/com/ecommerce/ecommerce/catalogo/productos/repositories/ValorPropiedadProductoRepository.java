package com.ecommerce.ecommerce.catalogo.productos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.ecommerce.catalogo.productos.entities.ValorPropiedadProducto;

@Repository
public interface ValorPropiedadProductoRepository extends JpaRepository<ValorPropiedadProducto, Long> {

}
