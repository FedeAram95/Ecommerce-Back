package com.ecommerce.ecommerce.catalogo.controllers.dev.categorias.create;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerce.catalogo.categorias.entities.Categoria;
import com.ecommerce.ecommerce.catalogo.categorias.entities.Subcategoria;
import com.ecommerce.ecommerce.catalogo.categorias.exceptions.CategoriaException;
import com.ecommerce.ecommerce.catalogo.categorias.repositories.CategoriaRepository;
import com.ecommerce.ecommerce.catalogo.categorias.services.CategoriaService;
import com.ecommerce.ecommerce.catalogo.categorias.services.SubcategoriaService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * API para la creación de categorias/subcategorias, destinada a desarrolladores solamente.
 */
@RestController
@RequestMapping("/api/dev")
@AllArgsConstructor
@Slf4j
public class CreacionCategoriasApi {

    private final CategoriaService categoriaService;
    private final CategoriaRepository categoriaRepository;
    private final SubcategoriaService subcategoriaService;

    
    @PostMapping("/categorias")
    public ResponseEntity<Categoria> createCategory(@RequestBody Categoria categoria) {
        Categoria nuevaCategoria = this.categoriaService.crear(categoria);
        return new ResponseEntity<>(nuevaCategoria, HttpStatus.CREATED);
    }

    
    @PostMapping("/categorias/{categoriaId}/subcategorias")
    public ResponseEntity<Subcategoria> createSubcategory(@RequestBody Subcategoria subcategoria,
                                                          @PathVariable Long categoriaId) {
        Categoria categoria;
        Subcategoria nuevaSubcategoria;

        try {
            categoria = this.categoriaService.obtenerCategoria(categoriaId);
            nuevaSubcategoria = this.subcategoriaService.crear(subcategoria);
            categoria.getSubcategorias().add(nuevaSubcategoria);
            this.categoriaRepository.save(categoria);
        } catch (CategoriaException e) {
            log.error(e.getMessage());
            return null;
        }

        return new ResponseEntity<>(nuevaSubcategoria, HttpStatus.CREATED);
    }

}
