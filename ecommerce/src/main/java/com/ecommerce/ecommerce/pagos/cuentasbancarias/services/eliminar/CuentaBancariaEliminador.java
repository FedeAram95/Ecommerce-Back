package com.ecommerce.ecommerce.pagos.cuentasbancarias.services.eliminar;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.pagos.cuentasbancarias.entities.CuentaBancaria;
import com.ecommerce.ecommerce.pagos.cuentasbancarias.exceptions.CuentaBancariaException;
import com.ecommerce.ecommerce.pagos.cuentasbancarias.services.CuentaBancariaDSGateway;

@Service
public class CuentaBancariaEliminador implements CuentaBancariaEliminadorService {

    private final CuentaBancariaDSGateway cuentaBancariaDSGateway;

    @Autowired
    public CuentaBancariaEliminador(CuentaBancariaDSGateway cuentaBancariaDSGateway) {
        this.cuentaBancariaDSGateway = cuentaBancariaDSGateway;
    }

    @Override
    public void eliminarCuentaBancaria(Long cuentaId) throws CuentaBancariaException {
        if (this.cuentaBancariaDSGateway.findAll().size() == 1)
            throw new CuentaBancariaException("No es posible eliminar la única cuenta existente");
        this.cuentaBancariaDSGateway.deleteById(cuentaId);
        this.marcarCuentaSiguiente();
    }

    /**
     * Método que encuentra la siguiente cuenta registrada y, si existe, la marca como principal.
     */
    private void marcarCuentaSiguiente() {
        Optional<CuentaBancaria> cuentaSiguienteOptional = this.cuentaBancariaDSGateway.findFirst();
        CuentaBancaria cuentaSiguiente;
        if (cuentaSiguienteOptional.isPresent()) {
            cuentaSiguiente = cuentaSiguienteOptional.get();
            cuentaSiguiente.marcarPrincipal();
            this.cuentaBancariaDSGateway.save(cuentaSiguiente);
        }
    }
}
