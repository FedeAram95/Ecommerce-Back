package com.ecommerce.ecommerce.catalogo.controllers.catalogoadmin.skus;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerce.catalogo.productos.exceptions.ProductoException;
import com.ecommerce.ecommerce.catalogo.services.CatalogoAdminService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/catalogo")
@AllArgsConstructor
public class EliminarSkuController {

    private final CatalogoAdminService catalogoAdminService;

    /**
     * Elimina un Sku (combinación de valores de producto).
     * URL: ~/api/catalogo/productos/skus/1
     * HttpMethod: DELETE
     * HttpStatus: OK
     * @param skuId PathVariable Long id del sku a eliminar.
     * @return ResponseEntity con mensaje de éxito/error al eliminar sku.
     */
    
    @DeleteMapping("/productos/skus/{skuId}")
    public ResponseEntity<?> eliminarSkuProducto(@PathVariable Long skuId) {
        Map<String, Object> response = new HashMap<>();
        String msg;

        try {
            this.catalogoAdminService.eliminarSku(skuId);
            msg = "Sku de producto eliminado con éxito";
        } catch (ProductoException e) {
            response.put("mensaje", "Error al eliminar sku de producto");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", msg);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
