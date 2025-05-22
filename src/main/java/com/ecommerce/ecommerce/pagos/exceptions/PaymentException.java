package com.ecommerce.ecommerce.pagos.exceptions;

public class PaymentException extends RuntimeException {
    public PaymentException(String exMensaje) {
        super(exMensaje);
    }
}
