package com.ecommerce.ecommerce.pagos.cuentasbancarias.controllers.find;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerce.pagos.cuentasbancarias.exceptions.CuentaBancariaException;
import com.ecommerce.ecommerce.pagos.cuentasbancarias.facade.CuentaBancariaResponse;
import com.ecommerce.ecommerce.pagos.cuentasbancarias.services.find.CuentaBancariaFinderService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/pagos")
@AllArgsConstructor
public class CuentaBancariaFindController {

    private final CuentaBancariaFinderService cuentaBancariaFinderService;

    /**
     * API para obtener una cuenta bancaria a través de su id. Esta API es publica para usuarios
     * autenticados (es decir, para admins/users).
     * URL: ~/api/pagos/cuentas-bancarias/1
     * HttpMethod: GET
     * HttpStatus: OK
     * @param cuentaId Long id de la cuenta a obtener.
     * @return ResponseEntity con la {@link CuentaBancariaResponse} con datos de la cuenta.
     */
    @GetMapping("/cuentas-bancarias/{cuentaId}")
    public ResponseEntity<Map<String, Object>> obtenerCuentaBancaria(@PathVariable Long cuentaId) {
        Map<String, Object> response = new HashMap<>();
        CuentaBancariaResponse cuentaResponse;

        try {
            cuentaResponse = this.cuentaBancariaFinderService.find(cuentaId);
        } catch (CuentaBancariaException e) {
            response.put("mensaje", "Error al obtener cuenta bancaria");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("cuenta", cuentaResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
