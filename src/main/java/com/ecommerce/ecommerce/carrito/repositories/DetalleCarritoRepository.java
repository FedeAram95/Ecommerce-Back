package com.ecommerce.ecommerce.carrito.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.ecommerce.carrito.entities.DetalleCarrito;
import com.ecommerce.ecommerce.catalogo.skus.entities.Sku;

@Repository
public interface DetalleCarritoRepository extends JpaRepository<DetalleCarrito, Long> {

    void deleteBySku(Sku sku);

}
