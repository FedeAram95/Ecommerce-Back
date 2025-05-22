package com.ecommerce.ecommerce.pagos.cuentasbancarias.services.find;

import java.util.List;

import com.ecommerce.ecommerce.pagos.cuentasbancarias.dto.CajaAhorroDto;

public interface CajaAhorroFinderService {
    List<CajaAhorroDto> listarCajasAhorro();
}
