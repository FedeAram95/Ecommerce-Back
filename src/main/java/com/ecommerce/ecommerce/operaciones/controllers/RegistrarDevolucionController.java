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
 * API utilizada para, como administrador de la tienda, poder registrar que se ha completado
 * la devolución de los items de una operación requerida.
 */
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class RegistrarDevolucionController {

    private final OperacionService operacionService;

    /**
     * API para registrar que una devolución de operación ha sido completada por un administrador.
     * URL: ~/api/operaciones/devolucion/registrar
     * HttpMethod: POST
     * HttpStatus: OK
     * @param nroOperacion Long nro de operación ha registrar como devuelta.
     * @return ResponseEntity con la operación en su nuevo estado.
     */
    
    @PostMapping("/operaciones/devolucion/registrar")
    public ResponseEntity<?> registrarDevolucion(@RequestParam Long nroOperacion) {
        Map<String, Object> response = new HashMap<>();
        Operacion operacionDevuelta;

        try {
            operacionDevuelta = this.operacionService.registrarDevolucion(nroOperacion);
        } catch (OperacionException e) {
            response.put("mensaje", "Error al registrar devolución de operación");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("operacion", operacionDevuelta);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
