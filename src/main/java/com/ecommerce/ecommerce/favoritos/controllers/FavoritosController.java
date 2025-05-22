package com.ecommerce.ecommerce.favoritos.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerce.autenticacion.exceptions.AutenticacionException;
import com.ecommerce.ecommerce.favoritos.entities.Favorito;
import com.ecommerce.ecommerce.favoritos.services.FavoritosService;
import com.ecommerce.ecommerce.perfiles.exceptions.PerfilesException;

import lombok.AllArgsConstructor;

/**
 * Controlador que se encarga de agregar/quitar items de productos al listado
 * de favoritos del perfil (usuario) logueado.
 */
@RestController
@RequestMapping("/api/perfil")
@AllArgsConstructor
public class FavoritosController {

    private final FavoritosService favoritosService;

    /**
     * API que se encarga de agregar un item de producto a favoritos del perfil actual.
     * URL: ~/api/perfil/favoritos/producto/1/agregar
     * HttpMethod: POST
     * HttpStatus: CREATED
     * @param productoId PathVariable Long id del producto a agregar a favoritos.
     * @return ResponseEntity con los favoritos actualizados.
     */
    @PostMapping("/favoritos/producto/{productoId}/agregar")
    public ResponseEntity<?> agregarProductoAFavoritos(@PathVariable Long productoId) {
        Map<String, Object> response = new HashMap<>();
        Favorito favoritoActualizado;

        try {
            favoritoActualizado = this.favoritosService.agregarFavorito(productoId);
        } catch (PerfilesException | AutenticacionException e) {
            response.put("mensaje", "Error al agregar producto a favoritos");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (favoritoActualizado == null) {
            response.put("mensaje", "El producto ya se encuentra en favoritos");
            return new ResponseEntity<>(response, HttpStatus.FOUND);
        }

        response.put("favoritos", favoritoActualizado);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * API que se encarga de quitar un item de producto de los favoritos del perfil actual.
     * URL: ~/api/perfil/favoritos/producto/1/quitar
     * HttpMethod: DELETE
     * HttpStatus: OK
     * @param productoId PathVariable Long id del producto a quitar.
     * @return ResponseEntity con los favoritos actualizados.
     */
    @DeleteMapping("/favoritos/producto/{productoId}/quitar")
    public ResponseEntity<?> quitarProductoDeFavoritos(@PathVariable Long productoId) {
        Map<String, Object> response = new HashMap<>();
        Favorito favoritoActualizado;

        try {
            favoritoActualizado = this.favoritosService.quitarFavorito(productoId);
        } catch (PerfilesException | AutenticacionException e) {
            response.put("mensaje", "Error al quitar producto de favoritos");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("favoritos", favoritoActualizado);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
