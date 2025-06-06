package com.ecommerce.ecommerce.compras.controllers.checkout;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerce.autenticacion.exceptions.AutenticacionException;
import com.ecommerce.ecommerce.compras.services.CompletarPagoService;
import com.ecommerce.ecommerce.operaciones.exceptions.OperacionException;
import com.ecommerce.ecommerce.pagos.entities.OperacionPago;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/perfil")
@AllArgsConstructor
public class CompletarCheckoutController {

    private final CompletarPagoService completarPagoService;

    /**
     * API utilizada por el usuario comprador para completar el pago de una operación (compra).
     * URL: ~/api/perfil/compras/1/completar/pago
     * HttpMethod: POST
     * HttpStatus: OK
     * @param nroOperacion Long nro de operación de la compra a completar su pago.
     * @return ResponseEntity con la info del pago a completar.
     */
    @PostMapping("/compras/{nroOperacion}/completar/pago")
    public ResponseEntity<?> completarPagoCompra(@PathVariable Long nroOperacion) {
        Map<String, Object> response = new HashMap<>();
        OperacionPago operacionPago;

        try {
            operacionPago = this.completarPagoService.completarPago(nroOperacion);
        } catch (AutenticacionException | OperacionException e) {
            response.put("mensaje", "Error al completar el pago");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("pago", operacionPago);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
