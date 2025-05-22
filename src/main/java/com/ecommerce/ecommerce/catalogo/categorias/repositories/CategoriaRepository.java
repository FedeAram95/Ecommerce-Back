package com.ecommerce.ecommerce.catalogo.categorias.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.ecommerce.catalogo.categorias.entities.Categoria;
import com.ecommerce.ecommerce.catalogo.categorias.entities.Subcategoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    List<Categoria> findAllByOrderByNombreAsc();

    Optional<Categoria> findByNombre(String nombre);

    Optional<Categoria> findBySubcategoriasContaining(Subcategoria subcategoria);
}
