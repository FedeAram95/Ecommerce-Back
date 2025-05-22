package com.ecommerce.ecommerce.pagos.factory;

import java.util.Map;

import com.ecommerce.ecommerce.operaciones.exceptions.OperacionException;
import com.ecommerce.ecommerce.pagos.entities.MedioPagoEnum;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OperacionPagoInfoFactory {

    public static OperacionPagoInfo getOperacionPagoInfo(String medioPago, Map<String, Object> atributos) {
        if (medioPago.equalsIgnoreCase(String.valueOf(MedioPagoEnum.EFECTIVO))) {
            return new EfectivoPagoInfo(atributos);
        } else if (medioPago.equalsIgnoreCase(String.valueOf(MedioPagoEnum.TRANSFERENCIA_BANCARIA))) {
            return new TransferenciaBancariaInfo(atributos);
        } else if (medioPago.equalsIgnoreCase(String.valueOf(MedioPagoEnum.PAYPAL))) {
            return new PayPalPagoInfo(atributos);
        } else if (medioPago.equalsIgnoreCase(String.valueOf(MedioPagoEnum.MERCADO_PAGO))) {
            return new MercadoPagoPagoInfo(atributos);
        } else {
            throw new OperacionException("Medio de pago " + medioPago + " aun" +
                    " no ha sido implementado");
        }
    }
}
