package com.ecommerce.ecommerce.favoritos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.ecommerce.catalogo.productos.entities.Producto;
import com.ecommerce.ecommerce.favoritos.entities.ItemFavorito;

@Repository
public interface ItemFavoritoRepository extends JpaRepository<ItemFavorito, Long> {

    void deleteByProducto(Producto producto);

}
