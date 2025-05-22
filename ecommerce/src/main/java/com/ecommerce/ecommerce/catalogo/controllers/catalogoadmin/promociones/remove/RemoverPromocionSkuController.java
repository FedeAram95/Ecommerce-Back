package com.ecommerce.ecommerce.catalogo.controllers.catalogoadmin.promociones.remove;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerce.catalogo.promociones.exceptions.PromocionException;
import com.ecommerce.ecommerce.catalogo.promociones.services.PromocionService;
import com.ecommerce.ecommerce.catalogo.skus.entities.Sku;
import com.ecommerce.ecommerce.catalogo.skus.exceptions.SkuException;
import com.ecommerce.ecommerce.catalogo.skus.services.SkuService;

import lombok.AllArgsConstructor;

/**
 * API para remover promociones de skus individuales.
 */
@RestController
@RequestMapping("/api/catalogo")
@AllArgsConstructor
public class RemoverPromocionSkuController {

    private final SkuService skuService;
    private final PromocionService promocionService;

    /**
     * API para remover la promoción para un Sku individual.
     * URL: ~/api/catalogo/productos/skus/1/promociones
     * HttpMethod: DELETE
     * HttpStatus: OK
     * @param skuId Long id del sku a remover su promoción.
     * @return ResponseEntity con mensaje éxito/error.
     */
    
    @DeleteMapping("/productos/skus/{skuId}/promociones")
    public ResponseEntity<?> removerPromocionSku(@PathVariable Long skuId) {
        Map<String, Object> response = new HashMap<>();
        String msg;

        try {
            Sku sku = this.skuService.obtenerSku(skuId);
            this.promocionService.removerPromocionSku(sku);

            msg = "Promoción del sku: " + skuId + " removida con éxito";
        } catch (SkuException | PromocionException e) {
            response.put("mensaje", "Error al remover promoción");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", msg);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
