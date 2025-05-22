package com.ecommerce.ecommerce.pagos.cuentasbancarias.services.crear;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.ecommerce.localizaciones.entities.Pais;
import com.ecommerce.ecommerce.pagos.cuentasbancarias.entities.CuentaBancaria;
import com.ecommerce.ecommerce.pagos.cuentasbancarias.exceptions.CuentaBancariaException;
import com.ecommerce.ecommerce.pagos.cuentasbancarias.facade.CuentaBancariaRequest;
import com.ecommerce.ecommerce.pagos.cuentasbancarias.facade.CuentaBancariaResponse;
import com.ecommerce.ecommerce.pagos.cuentasbancarias.services.CuentaBancariaDSGateway;
import com.ecommerce.ecommerce.pagos.cuentasbancarias.services.mapper.CuentaBancariaMapper;

@Service
public class CuentaBancariaCreador implements CuentaBancariaCreadorService {

    private final CuentaBancariaDSGateway cuentaBancariaDSGateway;
    private final CuentaBancariaMapper mapper;

    @Autowired
    public CuentaBancariaCreador(CuentaBancariaDSGateway cuentaBancariaDSGateway, CuentaBancariaMapper mapper) {
        this.cuentaBancariaDSGateway = cuentaBancariaDSGateway;
        this.mapper = mapper;
    }

    @Transactional
    @Override
    public CuentaBancariaResponse crearCuentaBancaria(CuentaBancariaRequest cuentaBancariaRequest) throws CuentaBancariaException {
        Pais paisCuenta = this.cuentaBancariaDSGateway.findPaisById(cuentaBancariaRequest.getPaisId());

        if (this.validarCuentaPaisExistente(paisCuenta))
            throw new CuentaBancariaException("Ya existe una cuenta para el país: " + paisCuenta.getNombre());

        CuentaBancaria cuentaBancaria = CuentaBancaria.crear(
                cuentaBancariaRequest.getNroCuenta(),
                cuentaBancariaRequest.getTipo(), cuentaBancariaRequest.getCa(),
                cuentaBancariaRequest.getAlias(), cuentaBancariaRequest.getEmail(),
                paisCuenta,cuentaBancariaRequest.getTitular(), cuentaBancariaRequest.getBanco());

        if (!this.existeCuentaPrincipal()) {
            cuentaBancaria.marcarPrincipal();
        }

        return this.mapToDto(this.cuentaBancariaDSGateway.save(cuentaBancaria));
    }

    /**
     * Verifica que no exista otra cuenta como principal.
     * @return boolean --> true: ya existe cuenta ppal, false: no existe.
     */
    private boolean existeCuentaPrincipal() {
        List<CuentaBancaria> cuentas = this.cuentaBancariaDSGateway.findAll();
        for (CuentaBancaria cuentaActual: cuentas) {
            if (cuentaActual.isPrincipal()) return true;
        }
        return false;
    }

    /**
     * Valida que exista una cuenta con dicho país.
     * @param paisAValidar requerido.
     * @return boolean --> true: ya existe una cuenta con país, false: no existe cuenta con país.
     */
    private boolean validarCuentaPaisExistente(Pais paisAValidar) {
        return this.cuentaBancariaDSGateway.existsByPais(paisAValidar);
    }

    /**
     * Mapea entidad {@link CuentaBancaria} creada, a una response {@link CuentaBancariaResponse}.
     * @param cuentaBancariaEntity {@link CuentaBancaria} a mapear.
     * @return {@link CuentaBancariaResponse} ya mapeada.
     */
    private CuentaBancariaResponse mapToDto(CuentaBancaria cuentaBancariaEntity) {
        return this.mapper.mapToResponse(cuentaBancariaEntity);
    }
}
