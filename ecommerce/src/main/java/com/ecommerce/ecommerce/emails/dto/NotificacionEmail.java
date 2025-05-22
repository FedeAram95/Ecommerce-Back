package com.ecommerce.ecommerce.emails.dto;

public interface NotificacionEmail {

    String getSubject();

    String getRecipient();

    Object getBody();

    String getRedirectUrl();
}
