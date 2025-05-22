package com.ecommerce.ecommerce.pagos.cuentasbancarias.dto;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TipoCuentaBancariaDto implements Serializable {
    private String tipo;

    public TipoCuentaBancariaDto() {

    }

    public TipoCuentaBancariaDto(String tipo) {
        this.tipo = tipo;
    }
}
