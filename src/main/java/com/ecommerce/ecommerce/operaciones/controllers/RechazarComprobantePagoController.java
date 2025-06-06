package com.ecommerce.ecommerce.operaciones.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerce.operaciones.entities.Operacion;
import com.ecommerce.ecommerce.operaciones.exceptions.OperacionException;
import com.ecommerce.ecommerce.operaciones.services.OperacionService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class RechazarComprobantePagoController {

    private final OperacionService operacionService;

    /**
     * API para rechazar el comprobante de pago para una operación, por el motivo que sea.
     * URL: ~/api/operaciones/1/rechazar/pago
     * HttpMethod: POST
     * HttpStatus: OK
     * @param nroOperacion Long numero de operación a rechazar su comprobante de pago.
     * @return ResponseEntity con la operación actualizada.
     */
    
    @PostMapping("/operaciones/rechazar/pago")
    public ResponseEntity<?> rechazarComprobantePago(@RequestParam Long nroOperacion) {
        Map<String, Object> response = new HashMap<>();
        Operacion operacionActualizada;

        try {
            operacionActualizada = this.operacionService.rechazarComprobantePago(nroOperacion);
        } catch (OperacionException e) {
            response.put("mensaje", "Error al rechazar comprobante de pago");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("operacion", operacionActualizada);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
