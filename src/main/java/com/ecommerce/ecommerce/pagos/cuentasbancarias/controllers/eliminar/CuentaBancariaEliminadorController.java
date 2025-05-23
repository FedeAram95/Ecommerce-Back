package com.ecommerce.ecommerce.pagos.cuentasbancarias.controllers.eliminar;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerce.pagos.cuentasbancarias.exceptions.CuentaBancariaException;
import com.ecommerce.ecommerce.pagos.cuentasbancarias.services.eliminar.CuentaBancariaEliminadorService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/pagos")
@AllArgsConstructor
public class CuentaBancariaEliminadorController {

    private final CuentaBancariaEliminadorService eliminadorService;

    /**
     * API para eliminar una cuenta bancaria existente.
     * URL: ~/api/pagos/cuentas-bancarias/1
     * HttpMethod: DELETE
     * HttpStatus: OK
     * @param cuentaId Long id de la cuenta bancaria a eliminar.
     * @return ResponseEntity con mensaje de éxito.
     */
    
    @DeleteMapping("/cuentas-bancarias/{cuentaId}")
    public ResponseEntity<Map<String, Object>> eliminarCuentaBancaria(@PathVariable Long cuentaId) {
        Map<String, Object> response = new HashMap<>();
        String msg;

        try {
            this.eliminadorService.eliminarCuentaBancaria(cuentaId);
            msg = "Cuenta Bancaria con id: " + cuentaId + " eliminada con éxito";
        } catch (CuentaBancariaException e) {
            response.put("mensaje", "Error al eliminar la cuenta bancaria");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", msg);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
