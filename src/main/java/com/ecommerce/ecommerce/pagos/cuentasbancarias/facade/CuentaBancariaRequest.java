package com.ecommerce.ecommerce.pagos.cuentasbancarias.facade;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.ecommerce.ecommerce.pagos.cuentasbancarias.entities.CajaAhorroEnum;
import com.ecommerce.ecommerce.pagos.cuentasbancarias.entities.TipoCuentaBancaria;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CuentaBancariaRequest implements Serializable {
    @NotNull(message = "El n° de CBU es obligatorio")
    @Length(min = 5, message = "El n° de cuenta debe tener al menos 5 caracteres")
    private String nroCuenta;
    @NotNull(message = "El tipo de cuenta es obligatorio")
    private TipoCuentaBancaria tipo;
    @NotNull(message = "La moneda de la caja de ahorro es obligatoria")
    private CajaAhorroEnum ca;
    @Length(min = 5, message = "El alias debe tener al menos 5 caracteres")
    private String alias;
    @NotNull(message = "El nombre del titular es obligatorio")
    private String titular;
    private String banco;
    private String email;
    @NotNull(message = "El pais es obligatorio")
    private Long paisId;

    public CuentaBancariaRequest() {

    }
}
