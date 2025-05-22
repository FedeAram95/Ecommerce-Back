package com.ecommerce.ecommerce.pagos.cuentasbancarias.controllers.crear;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerce.localizaciones.exceptions.LocalizationException;
import com.ecommerce.ecommerce.pagos.cuentasbancarias.exceptions.CuentaBancariaException;
import com.ecommerce.ecommerce.pagos.cuentasbancarias.facade.CuentaBancariaRequest;
import com.ecommerce.ecommerce.pagos.cuentasbancarias.facade.CuentaBancariaResponse;
import com.ecommerce.ecommerce.pagos.cuentasbancarias.services.crear.CuentaBancariaCreadorService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/pagos")
@AllArgsConstructor
public class CuentaBancariaCreacionController {

    private final CuentaBancariaCreadorService cuentaBancariaCreadorService;

    /**
     * API para crear una nueva cuenta bancaria, requerido por un usuario administrador.
     * URL: ~/api/pagos/cuentas-bancarias
     * HttpMethod: POST
     * HttpStatus: CREATED
     * @param cuentaBancariaRequest {@link CuentaBancariaRequest} con los datos de la nueva cuenta bancaria.
     * @return ResponseEntity con una {@link CuentaBancariaResponse} con los datos de la cuenta bancaria creada.
     */
    @PostMapping("/cuentas-bancarias")
    
    public ResponseEntity<Map<String, Object>> crearNuevaCuentaBancaria(
            @Valid @RequestBody CuentaBancariaRequest cuentaBancariaRequest, BindingResult result) {
        Map<String, Object> response = new HashMap<>();
        CuentaBancariaResponse cuentaResponse;

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> err.getField() + ": " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("error", "Bad Request");
            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            cuentaResponse = this.cuentaBancariaCreadorService.crearCuentaBancaria(cuentaBancariaRequest);
        } catch (LocalizationException | CuentaBancariaException e) {
            response.put("mensaje", "Error al crear nueva cuenta bancaria");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("cuenta", cuentaResponse);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
