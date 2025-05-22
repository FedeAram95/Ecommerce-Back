package com.ecommerce.ecommerce.catalogo.productos.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.ecommerce.catalogo.productos.entities.Marca;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, Long> {

    List<Marca> findAllByOrderByNombreAsc();

    Optional<Marca> findByNombre(String nombre);

    List<Marca> findAllByNombreContainingIgnoringCase(String nombre);
}
