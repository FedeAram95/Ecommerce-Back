package com.ecommerce.ecommerce.favoritos.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.ecommerce.catalogo.productos.entities.Producto;
import com.ecommerce.ecommerce.catalogo.productos.services.ProductoService;
import com.ecommerce.ecommerce.favoritos.entities.Favorito;
import com.ecommerce.ecommerce.favoritos.entities.ItemFavorito;
import com.ecommerce.ecommerce.favoritos.repositories.FavoritosRepository;
import com.ecommerce.ecommerce.favoritos.repositories.ItemFavoritoRepository;
import com.ecommerce.ecommerce.perfiles.services.PerfilService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class FavoritosServiceImpl implements FavoritosService {

    private final FavoritosRepository favoritosRepository;
    private final ItemFavoritoRepository itemFavoritoRepository;
    private final ProductoService productoService;
    private final PerfilService perfilService;

    @Transactional
    @Override
    public Favorito agregarFavorito(Long productoId) {
        boolean existeProd = false;
        Favorito favorito = this.perfilService.obtenerFavoritos();
        Producto productoAgregar = this.productoService.obtenerProducto(productoId);

        for (ItemFavorito item: favorito.getItems()) {
            if (item.getProducto().equals(productoAgregar)) {
                existeProd = true;
                log.info("Producto ya cargado en favs");
            }
        }

        if (!existeProd) {
            ItemFavorito item = ItemFavorito.builder()
                    .producto(productoAgregar).build();

            favorito.getItems().add(item);
            return this.favoritosRepository.save(favorito);
        }

        return null;
    }

    @Transactional
    @Override
    public Favorito quitarFavorito(Long productoId) {
        Favorito favorito = this.perfilService.obtenerFavoritos();
        Producto productoQuitar = this.productoService.obtenerProducto(productoId);

        favorito.getItems().removeIf(item -> item.getProducto().equals(productoQuitar));
        this.itemFavoritoRepository.deleteByProducto(productoQuitar);

        return this.favoritosRepository.save(favorito);
    }
}
