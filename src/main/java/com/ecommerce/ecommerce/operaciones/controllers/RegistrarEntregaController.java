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
 * API para registrar la entrega de los items de una operación. Esta API es accesible solo por los
 * administradores de la tienda online, para registrar como entregados los items de operaciones
 * correspondientes.
 */
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class RegistrarEntregaController {

    private final OperacionService operacionService;

    /**
     * API que registra que los items de una operación han sido entregados.
     * URL: ~/api/operaciones/entregar
     * HttpMethod: POST
     * HttpStatus: OK
     * @param nroOperacion @RequestParam Long con el numero de operacion a registrar entrega.
     * @return ResponseEntity con la operacion y su nuevo estado.
     */
    
    @PostMapping("/operaciones/entregar")
    public ResponseEntity<?> registrarEntrega(@RequestParam Long nroOperacion) {
        Map<String, Object> response = new HashMap<>();
        Operacion operacionEntregada;

        try {
            operacionEntregada = this.operacionService.entregar(nroOperacion);
        } catch (OperacionException e) {
            response.put("mensaje", "Error al registrar la entrega de esta operación");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("operacion", operacionEntregada);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
