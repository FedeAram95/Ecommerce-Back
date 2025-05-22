package com.ecommerce.ecommerce.catalogo.controllers.catalogoadmin.productos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerce.catalogo.productos.entities.Caracteristica;
import com.ecommerce.ecommerce.catalogo.productos.exceptions.ProductoException;
import com.ecommerce.ecommerce.catalogo.services.CatalogoAdminService;

import lombok.AllArgsConstructor;

/**
 * API que conecta con los servicios para la administración de las características de los productos.
 */
@RestController
@RequestMapping("/api/catalogo")
@AllArgsConstructor
public class CaracteristicaController {

    private final CatalogoAdminService catalogoAdminService;

    /**
     * API que se encarga de registrar una nueva {@link Caracteristica} y asociarla al producto
     * requerido, a través de su id.
     * URL: ~/api/catalogo/productos/1/caracteristicas
     * HttpMethod: POST
     * HttpStatus: CREATED
     * @param caracteristica {@link Caracteristica} nueva a registrar.
     * @param productoId Long id del producto.
     * @return ResponseEntity con la nueva característica.
     */
    
    @PostMapping("/productos/{productoId}/caracteristicas")
    public ResponseEntity<?> registrarCaracteristicaProducto(@Valid @RequestBody Caracteristica caracteristica,
                                                             @PathVariable Long productoId) {
        Map<String, Object> response = new HashMap<>();
        Caracteristica nuevaCaracteristica;

        try {
            nuevaCaracteristica = this.catalogoAdminService.crearCaracteristicaProducto(caracteristica, productoId);
        } catch (ProductoException e) {
            response.put("mensaje", "Error al registrar la nueva característica para el producto");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("caracteristica", nuevaCaracteristica);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * API que obtiene todas las {@link Caracteristica}s del producto requerido.
     * URL: ~/api/catalogo/productos/1/caracteristicas
     * HttpMethod: GET
     * HttpStatus: OK
     * @param productoId Long id del producto a listar sus características.
     * @return ResponseEntity con el listado de características del producto.
     */
    @GetMapping("/productos/{productoId}/caracteristicas")
    public ResponseEntity<?> listarCaracteristicasProducto(@PathVariable Long productoId) {
        Map<String, Object> response = new HashMap<>();
        List<Caracteristica> caracteristicasProducto;

        try {
            caracteristicasProducto = this.catalogoAdminService.listarCaracteristicasProducto(productoId);
        } catch (ProductoException e) {
            response.put("mensaje", "Error al listar las características del producto");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("caracteristicas", caracteristicasProducto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * API para modificar los datos de una {@link Caracteristica} en particular, a través de su id, y del
     * id del producto del que es parte.
     * URL: ~/api/catalogo/productos/1/caracteristicas/1
     * HttpMethod: PUT
     * HttpStatus: OK
     * @param caracteristica {@link Caracteristica} con los datos actualizados (nombre, valor).
     * @param caracteristicaId Long id de la característica a actualizar.
     * @param productoId Long id del producto al que pertenece la característica.
     * @return ResponseEntity con la característica actualizada.
     */
    
    @PutMapping("/productos/{productoId}/caracteristicas/{caracteristicaId}")
    public ResponseEntity<?> actualizarCaracteristicaProducto(@Valid @RequestBody Caracteristica caracteristica,
                                                              @PathVariable Long caracteristicaId,
                                                              @PathVariable Long productoId) {
        Map<String, Object> response = new HashMap<>();
        Caracteristica caracteristicaActualizada;

        try {
            caracteristicaActualizada = this.catalogoAdminService.modificarCaracteristicaProducto(caracteristica,
                    caracteristicaId, productoId);
        } catch (ProductoException e) {
            response.put("mensaje", "Error al actualizar datos de la característica");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("caracteristica", caracteristicaActualizada);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Esta API se encarga de eliminar una {@link Caracteristica} existente, que es parte de un producto,
     * a través de su id, y del producto.
     * URL: ~/api/catalogo/productos/1/caracteristicas/1
     * HttpMethod: DELETE
     * HttpStatus: OK
     * @param caracteristicaId Long id de la característica a eliminar.
     * @param productoId Long id del producto al que pertenece la característica.
     * @return ResponseEntity con mensaje de éxito/error.
     */
    
    @DeleteMapping("/productos/{productoId}/caracteristicas/{caracteristicaId}")
    public ResponseEntity<?> eliminarCaracteristicaProducto(@PathVariable Long caracteristicaId,
                                                            @PathVariable Long productoId) {
        Map<String, Object> response = new HashMap<>();
        String msg;

        try {
            this.catalogoAdminService.eliminarCaracteristicaProducto(caracteristicaId, productoId);
            msg = "Característica eliminada con éxito para el producto con id: " + productoId;
        } catch (ProductoException e) {
            response.put("mensaje", "Error al eliminar la característica del producto");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", msg);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
