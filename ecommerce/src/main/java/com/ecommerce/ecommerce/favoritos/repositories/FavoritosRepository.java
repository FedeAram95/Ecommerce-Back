package com.ecommerce.ecommerce.favoritos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.ecommerce.favoritos.entities.Favorito;

@Repository
public interface FavoritosRepository extends JpaRepository<Favorito, Long> {

}
