package com.ecommerce.ecommerce.catalogo.controllers.catalogoadmin.productos;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerce.catalogo.productos.entities.Producto;
import com.ecommerce.ecommerce.catalogo.productos.exceptions.ProductoException;
import com.ecommerce.ecommerce.catalogo.productos.services.ProductoService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/catalogo")
@AllArgsConstructor
public class DestacarProductoController {

    private final ProductoService productoService;

    /**
     * API para destacar/quitar de destacados un producto a través de su id.
     * URL: ~/api/catalogo/productos/destacar/1
     * HttpMethod: POST
     * HttpStatus: OK
     * @param producto {@link Producto} a destacar/quitar.
     * @param id Long id del producto a destacar/quitar.
     * @return ResponseEntity con los datos del producto actualizados.
     */
    
    @PostMapping("/productos/destacar/{id}")
    public ResponseEntity<?> destacarProducto(@RequestBody Producto producto, @PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        try {
            this.productoService.destacar(producto, id);
        } catch (ProductoException e) {
            response.put("mensaje", "Error al destacar/quitar al producto");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "Producto destacado/quitado con éxito");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
