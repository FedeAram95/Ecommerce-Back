package com.ecommerce.ecommerce.pagos.cuentasbancarias.services.find;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.pagos.cuentasbancarias.dto.CajaAhorroDto;
import com.ecommerce.ecommerce.pagos.cuentasbancarias.entities.CajaAhorroEnum;

@Service
public class CajaAhorroFinder implements CajaAhorroFinderService {

    @Override
    public List<CajaAhorroDto> listarCajasAhorro() {
        return Arrays.stream(CajaAhorroEnum.values())
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    /**
     * Mapea enum a caja de ahorro dto.
     * @param cajaAhorroEnum {@link CajaAhorroEnum}.
     * @return {@link CajaAhorroDto}.
     */
    private CajaAhorroDto mapToDto(CajaAhorroEnum cajaAhorroEnum) {
        return CajaAhorroDto.builder()
                .ca(cajaAhorroEnum.name()).build();
    }
}
