package com.ecommerce.ecommerce.pagos.cuentasbancarias.services.find;

import java.util.List;

import com.ecommerce.ecommerce.pagos.cuentasbancarias.dto.TipoCuentaBancariaDto;

public interface TipoCuentaFinderService {
    List<TipoCuentaBancariaDto> listarTiposCuenta();
}
