package com.ecommerce.ecommerce.pagos.cuentasbancarias.services.find;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.pagos.cuentasbancarias.entities.CuentaBancaria;
import com.ecommerce.ecommerce.pagos.cuentasbancarias.exceptions.CuentaBancariaException;
import com.ecommerce.ecommerce.pagos.cuentasbancarias.facade.CuentaBancariaResponse;
import com.ecommerce.ecommerce.pagos.cuentasbancarias.services.CuentaBancariaDSGateway;
import com.ecommerce.ecommerce.pagos.cuentasbancarias.services.mapper.CuentaBancariaMapper;

@Service
public class CuentaBancariaFinder implements CuentaBancariaFinderService {

    private final CuentaBancariaDSGateway cuentaBancariaDSGateway;
    private final CuentaBancariaMapper mapper;

    @Autowired
    public CuentaBancariaFinder(CuentaBancariaDSGateway cuentaBancariaDSGateway, CuentaBancariaMapper mapper) {
        this.cuentaBancariaDSGateway = cuentaBancariaDSGateway;
        this.mapper = mapper;
    }

    @Override
    public CuentaBancariaResponse find(Long cuentaId) throws CuentaBancariaException {
        CuentaBancaria cuenta = this.cuentaBancariaDSGateway.findById(cuentaId);
        return this.mapper.mapToResponse(cuenta);
    }

    @Override
    public CuentaBancariaResponse findPrincipal() throws CuentaBancariaException {
        CuentaBancaria cuenta = this.cuentaBancariaDSGateway.findByPrincipal();
        return this.mapper.mapToResponse(cuenta);
    }

    @Override
    public CuentaBancariaResponse findByPais(Long paisId) throws CuentaBancariaException {
        CuentaBancaria cuenta = this.cuentaBancariaDSGateway.findByPais(paisId);
        return this.mapper.mapToResponse(cuenta);
    }

    @Override
    public List<CuentaBancariaResponse> findCuentas() {
        return this.cuentaBancariaDSGateway.findAll()
                .stream()
                .map(mapper::mapToResponse)
                .collect(Collectors.toList());
    }

}
