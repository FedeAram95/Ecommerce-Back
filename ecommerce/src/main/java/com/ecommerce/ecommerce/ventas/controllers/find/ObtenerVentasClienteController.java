package com.ecommerce.ecommerce.ventas.controllers.find;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerce.operaciones.exceptions.OperacionException;
import com.ecommerce.ecommerce.perfiles.exceptions.PerfilesException;
import com.ecommerce.ecommerce.ventas.dto.VentaPayload;
import com.ecommerce.ecommerce.ventas.services.VentasClienteFinder;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ObtenerVentasClienteController {

    private final VentasClienteFinder ventasClienteFinder;

    /**
     * API para obtener el listado de todas las ventas relacionadas a un cliente requerido, a través
     * de su id.
     * URL: ~/api/ventas/clientes/1
     * HttpMethod: GET
     * HttpStatus: OK
     * @param clienteId Long id del cliente a listar sus ventas.
     * @return ResponseEntity con listado de ventas del cliente.
     */
    
    @GetMapping("/ventas/clientes/{clienteId}")
    public ResponseEntity<?> obtenerVentasDeCliente(@PathVariable Long clienteId) {
        Map<String, Object> response = new HashMap<>();
        List<VentaPayload> ventasCliente;

        try {
            ventasCliente = this.ventasClienteFinder.ventasCliente(clienteId);
        } catch (PerfilesException | OperacionException e) {
            response.put("mensaje", "Error al obtener ventas del cliente");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("ventas", ventasCliente);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
