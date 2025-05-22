package com.ecommerce.ecommerce.pagos.cuentasbancarias.services.find;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.pagos.cuentasbancarias.dto.TipoCuentaBancariaDto;
import com.ecommerce.ecommerce.pagos.cuentasbancarias.entities.TipoCuentaBancaria;

@Service
public class TipoCuentaFinder implements TipoCuentaFinderService {

    @Override
    public List<TipoCuentaBancariaDto> listarTiposCuenta() {
        return Arrays.stream(TipoCuentaBancaria.values())
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    /**
     * Mapea enum a dto para la response.
     * @param tipoCuentaBancaria {@link TipoCuentaBancaria} enum con tipos existentes (fijo).
     * @return {@link TipoCuentaBancariaDto}.
     */
    private TipoCuentaBancariaDto mapToDto(TipoCuentaBancaria tipoCuentaBancaria) {
        return TipoCuentaBancariaDto.builder()
                .tipo(tipoCuentaBancaria.name()).build();
    }
}
