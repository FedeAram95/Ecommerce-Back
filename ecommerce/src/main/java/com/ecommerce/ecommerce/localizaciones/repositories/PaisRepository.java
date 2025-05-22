package com.ecommerce.ecommerce.localizaciones.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.ecommerce.localizaciones.entities.Pais;

@Repository
public interface PaisRepository extends JpaRepository<Pais, Long> {

    Optional<Pais> findByNombre(String pais);

    Optional<Pais> findByIso2(String iso2);

    List<Pais> findAllByOrderByNombreAsc();

}
