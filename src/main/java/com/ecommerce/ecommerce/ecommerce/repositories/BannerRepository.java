package com.ecommerce.ecommerce.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ecommerce.ecommerce.ecommerce.entities.Banner;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Long> {

    boolean existsByOrden(Integer orden);

    @Query("SELECT MAX(b.orden) as Orden FROM Banner b")
    Integer ultimoOrden();

}
