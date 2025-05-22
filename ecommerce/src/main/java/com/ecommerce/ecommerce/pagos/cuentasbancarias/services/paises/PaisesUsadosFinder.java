package com.ecommerce.ecommerce.pagos.cuentasbancarias.services.paises;

import java.util.List;

import com.ecommerce.ecommerce.pagos.cuentasbancarias.dto.PaisDto;

public interface PaisesUsadosFinder {

    /**
     * Encuentra y devuelve listado de paises que están siendo usados realmente por cuentas
     * bancarias.
     * @return {@link List<PaisDto>} listado de paises.
     */
    List<PaisDto> findPaisesEnCuentas();
}
