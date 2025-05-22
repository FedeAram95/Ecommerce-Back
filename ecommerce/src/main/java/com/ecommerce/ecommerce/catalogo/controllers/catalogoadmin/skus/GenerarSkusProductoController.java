package com.ecommerce.ecommerce.catalogo.controllers.catalogoadmin.skus;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerce.catalogo.productos.exceptions.ProductoException;
import com.ecommerce.ecommerce.catalogo.services.CatalogoAdminService;
import com.ecommerce.ecommerce.catalogo.skus.exceptions.SkuException;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/catalogo")
@AllArgsConstructor
public class GenerarSkusProductoController {

    private final CatalogoAdminService catalogoAdminService;

    /**
     * API para generar todas las combinaciones posibles de {@link com.ecommerce.ecommerce.catalogo.skus.entities.Sku}s
     * para un producto requerido, a través de su id.
     * URL: ~/api/catalogo/productos/1/generarSkus
     * HttpMethod: POST
     * HttpStatus: CREATED
     * @param productoId Long id del producto a generar sus skus.
     * @return ResponseEntity con datos: total combinaciones generadas, skus generados.
     */
    
    @PostMapping("/productos/{productoId}/generarSkus")
    public ResponseEntity<?> generarSkusProducto(@PathVariable Long productoId) {
        Map<String, Object> response = new HashMap<>();

        try {
            response = this.catalogoAdminService.generarSkusProducto(productoId);
        } catch (ProductoException | SkuException e) {
            response.put("mensaje", "Error al generar los SKUs del producto");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
