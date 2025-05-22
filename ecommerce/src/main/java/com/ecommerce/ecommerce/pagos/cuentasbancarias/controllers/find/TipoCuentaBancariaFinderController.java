package com.ecommerce.ecommerce.pagos.cuentasbancarias.controllers.find;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerce.pagos.cuentasbancarias.services.find.TipoCuentaFinderService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/pagos")
@AllArgsConstructor
public class TipoCuentaBancariaFinderController {

    private final TipoCuentaFinderService finderService;

    /**
     * API para listar todos los tipos de cuenta bancaria.
     * URL: ~/api/pagos/cuentas-bancarias/tipos
     * HttpMethod: GET
     * HttpStatus: OK
     * @return ResponseEntity con listado de tipos de cuenta bancaria.
     */
    
    @GetMapping("/cuentas-bancarias/tipos")
    public ResponseEntity<Map<String, Object>> listarTiposCuentaBancaria() {
        Map<String, Object> response = new HashMap<>();
        response.put("tipos", this.finderService.listarTiposCuenta());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
