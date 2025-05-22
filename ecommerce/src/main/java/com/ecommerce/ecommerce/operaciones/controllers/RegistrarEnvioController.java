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
public class RegistrarEnvioController {

    private final OperacionService operacionService;

    /**
     * Registra el envio de un pedido perteneciente a una operación.
     * URL: ~/api/operaciones/enviar
     * HttpMethod: POST
     * HttpStatus: OK
     * @param nroOperacion @RequestParam Long con el numero de operación a enviar.
     * @return ResponseEntity con la Operacion y su nuevo estado.
     */
    
    @PostMapping("/operaciones/enviar")
    public ResponseEntity<?> registrarEnvio(@RequestParam Long nroOperacion) {
        Map<String, Object> response = new HashMap<>();
        Operacion operacionEnviada;

        try {
            operacionEnviada = this.operacionService.enviar(nroOperacion);
        } catch (OperacionException e) {
            response.put("mensaje", "Error al registrar el envío de esta operación");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("operacionEnviada", operacionEnviada);
        return new ResponseEntity<>(operacionEnviada, HttpStatus.OK);
    }
}
