package com.ecommerce.ecommerce.catalogo.promociones.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.ecommerce.catalogo.promociones.entities.Promocion;

@Repository
public interface PromocionRepository extends JpaRepository<Promocion, Long> {

}
