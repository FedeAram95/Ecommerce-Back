package com.ecommerce.ecommerce.carrito.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerce.autenticacion.exceptions.AutenticacionException;
import com.ecommerce.ecommerce.carrito.entities.Carrito;
import com.ecommerce.ecommerce.carrito.exceptions.CarritoException;
import com.ecommerce.ecommerce.carrito.services.CarritoService;
import com.ecommerce.ecommerce.catalogo.skus.exceptions.SkuException;
import com.ecommerce.ecommerce.clientes.exceptions.ClienteException;
import com.ecommerce.ecommerce.perfiles.exceptions.PerfilesException;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/perfil")
@AllArgsConstructor
public class CarritoController {

    private final CarritoService carritoService;

    /**
     * Añade un nuevo item con Sku al carrito del perfil actual. Si ya existe en el carrito, agrega
     * uno a la cantidad.
     * URL: ~/api/perfil/carrito/item/agregar
     * HttpMethod: POST
     * HttpStatus: CREATED
     * @param skuId Long id del sku a agregar.
     * @param cantidad Integer cantidad que tendrá el item.
     * @return ResponseEntity con el carrito actualizado.
     */
    @PostMapping("/carrito/item/agregar")
    public ResponseEntity<?> agregarItem(@RequestParam Long skuId,
                                         @RequestParam(required = false, defaultValue = "1") Integer cantidad) {
        Map<String, Object> response = new HashMap<>();
        Carrito carrito;

        try {
            carrito = this.carritoService.agregarItem(skuId, cantidad);
        } catch (CarritoException | PerfilesException | SkuException | ClienteException | AutenticacionException e) {
            response.put("mensaje", "Error al agregar item al carrito");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("carrito", carrito);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Actualiza la cantidad de un item en el carrito.
     * URL: ~/api/perfil/carrito/item/actualizar
     * HttpMethod: PUT
     * HttpStatus: CREATED
     * @param skuId Long id del sku a actualizar cantidad en carrito.
     * @param cantidad Integer cantidad a agregar (o quitar).
     * @return ResponseEntity con el carrito actualizado.
     */
    @PutMapping("/carrito/item/actualizar")
    public ResponseEntity<?> actualizarCantidad(@RequestParam Long skuId,
                                                @RequestParam Integer cantidad) {
        Map<String, Object> response = new HashMap<>();
        Carrito carritoActualizado;

        try {
            carritoActualizado = this.carritoService.actualizarCantidad(skuId, cantidad);
        } catch (SkuException | PerfilesException | ClienteException | CarritoException | AutenticacionException e) {
            response.put("mensaje", "Error al actualizar la cantidad de skus en el carrito");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("carrito", carritoActualizado);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Quita un item con Sku del carrito del perfil.
     * URL: ~/api/perfil/carrito/item/quitar
     * HttpMethod: DELETE
     * HttpStatus: OK
     * @param skuId Long id del sku a quitar del carrito.
     * @return ResponseEntity del carrito actualizado.
     */
    @DeleteMapping("/carrito/item/quitar")
    public ResponseEntity<?> quitarItem(@RequestParam Long skuId) {
        Map<String, Object> response = new HashMap<>();
        Carrito carritoActualizado;

        try {
            carritoActualizado = this.carritoService.quitarItem(skuId);
        } catch (CarritoException | PerfilesException | SkuException | AutenticacionException e) {
            response.put("mensaje", "Error al quitar el item del carrito");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("carrito", carritoActualizado);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Vacía el carrito del perfil.
     * URL: ~/api/perfil/carrito/vaciar
     * HttpMethod: DELETE
     * HttpStatus: OK
     * @return ResponseEntity mensaje éxito/error.
     */
    @DeleteMapping("/carrito/vaciar")
    public ResponseEntity<String> vaciarCarrito() {
        try {
            this.carritoService.vaciar();
        } catch (PerfilesException | CarritoException | AutenticacionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("Carrito vaciado", HttpStatus.OK);
    }
}
