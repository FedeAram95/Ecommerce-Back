package com.ecommerce.ecommerce.autenticacion.exceptions;

public class TokenException extends RuntimeException {
    public TokenException(String exMensaje) {
        super(exMensaje);
    }
}
