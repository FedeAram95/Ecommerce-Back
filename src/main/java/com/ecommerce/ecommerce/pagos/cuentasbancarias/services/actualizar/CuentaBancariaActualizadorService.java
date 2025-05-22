package com.ecommerce.ecommerce.pagos.cuentasbancarias.services.actualizar;

import com.ecommerce.ecommerce.pagos.cuentasbancarias.exceptions.CuentaBancariaException;
import com.ecommerce.ecommerce.pagos.cuentasbancarias.facade.CuentaBancariaRequest;
import com.ecommerce.ecommerce.pagos.cuentasbancarias.facade.CuentaBancariaResponse;
import com.ecommerce.ecommerce.pagos.cuentasbancarias.facade.CuentaBancariaUpdateRequest;

public interface CuentaBancariaActualizadorService {

    /**
     * Servicio que se utiliza para modificar los datos de una cuenta bancaria.
     * @param cuentaId Long id de la cuenta a actualizar datos.
     * @param cuentaBancariaRequest {@link CuentaBancariaRequest} con los datos para actualizar.
     * @return {@link CuentaBancariaResponse} con datos de la cuenta actualizada.
     * @throws CuentaBancariaException si no se encuentra la entity de la cuenta.
     */
    CuentaBancariaResponse actualizarDatos(Long cuentaId, CuentaBancariaUpdateRequest cuentaBancariaRequest) throws CuentaBancariaException;
}
