package com.ecommerce.ecommerce.catalogo.skus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.ecommerce.catalogo.skus.entities.Sku;

@Repository
public interface SkuRepository extends JpaRepository<Sku, Long> {

}
