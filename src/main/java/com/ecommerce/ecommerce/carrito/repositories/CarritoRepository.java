package com.ecommerce.ecommerce.carrito.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.ecommerce.carrito.entities.Carrito;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Long> {

}
