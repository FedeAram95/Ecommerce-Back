package com.ecommerce.ecommerce.catalogo.controllers.catalogoadmin.promociones.remove;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerce.catalogo.productos.entities.Producto;
import com.ecommerce.ecommerce.catalogo.productos.exceptions.ProductoException;
import com.ecommerce.ecommerce.catalogo.productos.services.ProductoService;
import com.ecommerce.ecommerce.catalogo.promociones.exceptions.PromocionException;
import com.ecommerce.ecommerce.catalogo.promociones.services.PromocionService;

import lombok.AllArgsConstructor;

/**
 * API para remover promociones para productos (y sus skus).
 */
@RestController
@RequestMapping("/api/catalogo")
@AllArgsConstructor
public class RemoverPromocionProductoController {

    private final ProductoService productoService;
    private final PromocionService promocionService;

    /**
     * API para eliminar la Promoción para un producto requerido, a través de su id.
     * <br>
     * La API recibe como parámetro un booleano "afectaSkus", para definir los siguientes comportamientos:
     *  - true: las promociones de los skus del producto serán también removidas.
     *  - false: las promociones de los skus, si es que tienen, permanecen intactas. Solo se remueve la
     *  promoción que afecta al producto y su defaultSku.
     *  <br>
     *  Aclaración: Eliminar solo promoción del producto, si tiene skus adicionales, solo implica una eliminación
     *  visual, es decir, el producto NO tendrá vinculada ninguna promoción, pero sus skus adicionales SI tendrán
     *  sus promociones en caso de tenerlas.
     *  URL: ~/api/catalogo/productos/1/promociones
     *  HttpMethod: DELETE
     *  HttpStatus: OK
     * @param productoId Long id del producto a remover promoción/es.
     * @param afectaSkus boolean indicando si la eliminación debe o no afectar a skus add del producto.
     * @return ResponseEntity indicando éxito/error.
     */
    
    @DeleteMapping("/productos/{productoId}/promociones")
    public ResponseEntity<?> removerPromocionProducto(@PathVariable Long productoId,
                                                      @RequestParam(name = "afectaSkus") boolean afectaSkus) {
        Map<String, Object> response = new HashMap<>();
        String msg;

        try {
            Producto producto = this.productoService.obtenerProducto(productoId);

            if (afectaSkus) this.promocionService.removerPromocionProducto(producto);
            else this.promocionService.removerPromocionProductoSinSkus(producto);
            msg = "Promociones eliminadas con éxito";
        } catch (ProductoException | PromocionException e) {
            response.put("mensaje", "Error al remover promoción");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", msg);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
