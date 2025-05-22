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

/**
 * API para registrar la anulación de una operación por parte del administrador.
 */
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class RegistrarAnulacionController {

    private final OperacionService operacionService;

    /**
     * API para anular una operación como administrador.
     * URL: ~/api/operaciones/anular
     * HttpMethod: POST
     * HttpStatus: OK
     * @param nroOperacion Long nro de operación a anular.
     * @return ResponseEntity con la operación y su nuevo estado.
     */
    
    @PostMapping("/operaciones/anular")
    public ResponseEntity<?> registrarAnulacion(@RequestParam Long nroOperacion) {
        Map<String, Object> response = new HashMap<>();
        Operacion operacionAnulada;

        try {
            operacionAnulada = this.operacionService.anular(nroOperacion);
        } catch (OperacionException e) {
            response.put("mensaje", "Error al anular operación");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("operacion", operacionAnulada);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
