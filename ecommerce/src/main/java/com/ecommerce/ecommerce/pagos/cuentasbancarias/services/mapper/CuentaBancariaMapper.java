package com.ecommerce.ecommerce.pagos.cuentasbancarias.services.mapper;

import com.ecommerce.ecommerce.pagos.cuentasbancarias.entities.CuentaBancaria;
import com.ecommerce.ecommerce.pagos.cuentasbancarias.facade.CuentaBancariaResponse;

/**
 * Clase que se encarga de mappear los objetos de entidad -> facade y viceversa.
 */
public interface CuentaBancariaMapper {
    CuentaBancariaResponse mapToResponse(CuentaBancaria cuentaBancariaEntity);
}
