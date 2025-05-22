package com.ecommerce.ecommerce.autenticacion.exceptions;

public class MailSenderException extends RuntimeException {
    public MailSenderException(String exMensaje) {
        super(exMensaje);
    }
}
