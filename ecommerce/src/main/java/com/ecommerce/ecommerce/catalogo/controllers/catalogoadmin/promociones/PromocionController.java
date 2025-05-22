package com.ecommerce.ecommerce.catalogo.controllers.catalogoadmin.promociones;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerce.catalogo.productos.entities.Producto;
import com.ecommerce.ecommerce.catalogo.productos.exceptions.ProductoException;
import com.ecommerce.ecommerce.catalogo.promociones.entities.Promocion;
import com.ecommerce.ecommerce.catalogo.promociones.exceptions.PromocionException;
import com.ecommerce.ecommerce.catalogo.promociones.services.PromocionService;
import com.ecommerce.ecommerce.catalogo.skus.entities.Sku;
import com.ecommerce.ecommerce.catalogo.skus.exceptions.SkuException;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/catalogo")
@AllArgsConstructor
public class PromocionController {

    private final PromocionService promocionService;

    /**
     * Crea y asigna una Promocion nueva a un Producto existente.
     * URL: ~/api/catalogo/productos/1/promociones
     * HttpMethod: POST
     * HttpStatus: CREATED
     * @param productoId PathVariable Long id del producto.
     * @param promocion  RequestBody Promocion a crear.
     * @return Producto promocionado.
     */
    
    @PostMapping("/productos/{productoId}/promociones")
    public ResponseEntity<?> crearPromocionProducto(@PathVariable Long productoId,
                                                    @Valid @RequestBody Promocion promocion) {
        Map<String, Object> response = new HashMap<>();
        Producto productoPromocionado;

        try {
            productoPromocionado = this.promocionService.programarOfertaProducto(productoId, promocion);
        } catch (ProductoException | SkuException | PromocionException e) {
            response.put("mensaje", "Error al crear la promoción para el producto");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("producto", productoPromocionado);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Crea y asigna una Promocion nueva a un Sku existente.
     * URL: ~/api/catalogo/productos/skus/1/promociones
     * HttpMethod: POST
     * HttpStatus: CREATED
     * @param skuId PathVariable Long id del sku.
     * @param promocion  RequestBody Promocion a crear.
     * @return Sku promocionado.
     */
    
    @PostMapping("/productos/skus/{skuId}/promociones")
    public ResponseEntity<?> crearPromocionSku(@PathVariable Long skuId,
                                               @Valid @RequestBody Promocion promocion) {
        Map<String, Object> response = new HashMap<>();
        Sku skuPromocionado;

        try {
            skuPromocionado = this.promocionService.programarOfertaSku(skuId, promocion);
        } catch (ProductoException | SkuException | PromocionException e) {
            response.put("mensaje", "Error al crear la promoción para el sku");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("sku", skuPromocionado);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Crea y asigna promociones para todos los productos pertenecientes a una subcategoría requerida.
     * Devuelve el número total de productos que fueron afectados por la promoción (cantidad de productos
     * que pertenecen a la subcategoría a promocionar).
     * URL: ~/api/catalogo/subcategorias/1/promociones
     * HttpMethod: POST
     * HttpStatus: CREATED
     * @param subcategoriaId PathVariable Long id de la subcategoría.
     * @param promocion RequestBody Promocion a aplicar.
     * @return ResponseEntity con la cantidad de productos promocionados.
     */
    
    @PostMapping("/subcategorias/{subcategoriaId}/promociones")
    public ResponseEntity<?> crearPromocionProductosDeSubcategoria(@PathVariable Long subcategoriaId,
                                                                   @Valid @RequestBody Promocion promocion) {
        Map<String, Object> response = new HashMap<>();
        Integer cantProductosPromocionados;

        try {
            cantProductosPromocionados = this.promocionService.programarOfertaProductosSubcategoria(subcategoriaId,
                    promocion);
        } catch (ProductoException | SkuException | PromocionException e) {
            response.put("mensaje", "Error al crear promociones para los productos de la subcategoría");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("productosPromocionados", cantProductosPromocionados);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Obtiene la promoción actual de un producto requerido.
     * URL: ~/api/catalogo/productos/1/promociones
     * HttpMethod: GET
     * HttpStatus: OK
     * @param productoId PathVariable Long id del producto a obtener promoción.
     * @return ResponseEntity con la promoción.
     */
    @GetMapping("/productos/{productoId}/promociones")
    public ResponseEntity<?> obtenerPromocionProducto(@PathVariable Long productoId) {
        Map<String, Object> response = new HashMap<>();
        Promocion promocionProducto;

        try {
            promocionProducto = this.promocionService.obtenerPromocionProducto(productoId);
        } catch (ProductoException | SkuException | PromocionException e) {
            response.put("mensaje", "Error al obtener la promoción del producto");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("promocion", promocionProducto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Obtiene la promoción actual de un sku requerido.
     * URL: ~/api/catalogo/productos/skus/1/promociones
     * HttpMethod: GET
     * HttpStatus: OK
     * @param skuId PathVariable Long id del sku a obtener promoción.
     * @return ResponseEntity con la promoción.
     */
    @GetMapping("/productos/skus/{skuId}/promociones")
    public ResponseEntity<?> obtenerPromocionSku(@PathVariable Long skuId) {
        Map<String, Object> response = new HashMap<>();
        Promocion promocionProducto;

        try {
            promocionProducto = this.promocionService.obtenerPromocionSku(skuId);
        } catch (ProductoException | SkuException | PromocionException e) {
            response.put("mensaje", "Error al obtener la promoción del sku");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("promocion", promocionProducto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
