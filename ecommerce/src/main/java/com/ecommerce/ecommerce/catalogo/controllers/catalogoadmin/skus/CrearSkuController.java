package com.ecommerce.ecommerce.catalogo.controllers.catalogoadmin.skus;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerce.catalogo.productos.exceptions.ProductoException;
import com.ecommerce.ecommerce.catalogo.services.CatalogoAdminService;
import com.ecommerce.ecommerce.catalogo.skus.entities.Sku;
import com.ecommerce.ecommerce.catalogo.skus.exceptions.SkuException;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/catalogo")
@AllArgsConstructor
public class CrearSkuController {

    private final CatalogoAdminService catalogoAdminService;

    /**
     * API para crear un nuevo {@link Sku} para un producto requerido.
     * URL: ~/api/catalogo/productos/1/skus
     * HttpMethod: POST
     * HttpStatus: CREATED
     * @param productoId Long id del producto a crear un sku.
     * @param sku {@link Sku} nuevo a crear.
     * @return ResponseEntity con el nuevo sku creado.
     */
    
    @PostMapping("/productos/{productoId}/skus")
    public ResponseEntity<?> crearNuevoSkuProducto(@PathVariable Long productoId, @Valid @RequestBody Sku sku) {
        Map<String, Object> response = new HashMap<>();
        Sku nuevoSku;

        try {
            nuevoSku = this.catalogoAdminService.crearSku(productoId, sku);
        } catch (ProductoException | SkuException e) {
            response.put("mensaje", "Error al crear nuevo Sku de producto");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("sku", nuevoSku);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
