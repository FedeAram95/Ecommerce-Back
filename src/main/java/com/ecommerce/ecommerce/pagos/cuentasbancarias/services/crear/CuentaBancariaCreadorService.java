package com.ecommerce.ecommerce.pagos.cuentasbancarias.services.crear;

import com.ecommerce.ecommerce.pagos.cuentasbancarias.exceptions.CuentaBancariaException;
import com.ecommerce.ecommerce.pagos.cuentasbancarias.facade.CuentaBancariaRequest;
import com.ecommerce.ecommerce.pagos.cuentasbancarias.facade.CuentaBancariaResponse;

public interface CuentaBancariaCreadorService {

    /**
     * Crea una nueva CuentaBancaria a partir de los datos requeridos en una {@link CuentaBancariaRequest}.
     * Asigna el país requerido a la cuenta creada.
     * @param cuentaBancariaRequest {@link CuentaBancariaRequest} con los datos para la creación.
     * @return {@link CuentaBancariaResponse} con los datos de la cuenta bancaria creada.
     * @throws CuentaBancariaException si no se puede crear la cuenta por algún motivo.
     */
    CuentaBancariaResponse crearCuentaBancaria(CuentaBancariaRequest cuentaBancariaRequest) throws CuentaBancariaException;
}
