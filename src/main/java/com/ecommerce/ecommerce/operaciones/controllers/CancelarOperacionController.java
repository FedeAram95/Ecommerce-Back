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
@RequestMapping("api")
@AllArgsConstructor
public class CancelarOperacionController {

    private final OperacionService operacionService;

    /**
     * Registra la cancelación, por parte del usuario cliente, de una operación realizada que aun
     * no ha sido enviada.
     * URL: ~/api/operaciones/cancelar
     * HttpMethod: POST
     * HttpStatus: OK
     * @param nroOperacion @RequestParam Long numero de operación a cancelar.
     * @return ResponseEntity Operacion con su nuevo estado.
     */
    @PostMapping("/operaciones/cancelar")
    public ResponseEntity<?> cancelarOperacion(@RequestParam Long nroOperacion) {
        Map<String, Object> response = new HashMap<>();
        Operacion operacionCancelada;

        try {
            operacionCancelada = this.operacionService.cancelar(nroOperacion);
        } catch (OperacionException e) {
            response.put("mensaje", "Error al cancelar operación");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("operacionCancelada", operacionCancelada);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
