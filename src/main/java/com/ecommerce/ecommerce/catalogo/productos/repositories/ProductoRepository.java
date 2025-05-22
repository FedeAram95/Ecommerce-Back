package com.ecommerce.ecommerce.catalogo.productos.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.ecommerce.catalogo.categorias.entities.Subcategoria;
import com.ecommerce.ecommerce.catalogo.productos.entities.Marca;
import com.ecommerce.ecommerce.catalogo.productos.entities.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findAllByOrderByNombreAsc();

    List<Producto> findAllByDestacadoIsTrueAndActivoIsTrue();

    List<Producto> findAllByNombreContainingIgnoringCaseAndActivoIsTrueOrderByNombreAsc(String termino);

    List<Producto> findAllBySubcategoriaAndActivoIsTrue(Subcategoria subcategoria);

    List<Producto> findAllBySubcategoria(Subcategoria subcategoria);

    List<Producto> findAllByMarcaAndActivoIsTrue(Marca marca);

    List<Producto> findAllByMarca(Marca marca);

    List<Producto> findAllByOrderByPrecioAsc();

    List<Producto> findAllByOrderByPrecioDesc();

    List<Producto> findAllByPrecioBetween(Double min, Double max);
}
