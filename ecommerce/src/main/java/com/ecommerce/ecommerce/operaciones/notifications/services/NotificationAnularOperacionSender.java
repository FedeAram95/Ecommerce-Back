package com.ecommerce.ecommerce.operaciones.notifications.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.notificaciones.dto.Notification;
import com.ecommerce.ecommerce.notificaciones.dto.NotificationConstants;
import com.ecommerce.ecommerce.notificaciones.services.NotificationSender;
import com.ecommerce.ecommerce.operaciones.entities.Operacion;

@Service("anularOperacionSender")
public class NotificationAnularOperacionSender extends NotificationSender {

    // Constructor con @Value para inyectar propiedades
    public NotificationAnularOperacionSender(
            @Value("${magicbell.apiKey}") String magicBellApiKey,
            @Value("${magicbell.apiSecret}") String magicBellApiSecret) {
        super(magicBellApiKey, magicBellApiSecret);
    }

    @Override
    public Notification doBuild(String title, Object helper, String actionUrl, String user) {
        Operacion operacion = (Operacion) helper;
        Long nroOperacion = operacion.getNroOperacion();
        String content = "Tu compra con N° " + nroOperacion + " fue anulada";

        return Notification.builder()
                .title(title)
                .content(content)
                .category(NotificationConstants.CATEGORY_NEW_MESSAGE)
                .actionUrl(actionUrl)
                .user(user)
                .build();
    }
}
