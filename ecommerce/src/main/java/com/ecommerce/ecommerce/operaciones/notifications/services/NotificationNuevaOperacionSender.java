package com.ecommerce.ecommerce.operaciones.notifications.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.notificaciones.dto.Notification;
import com.ecommerce.ecommerce.notificaciones.dto.NotificationConstants;
import com.ecommerce.ecommerce.notificaciones.services.NotificationSender;
import com.ecommerce.ecommerce.operaciones.entities.Operacion;

@Service(value = "nuevaOperacionSender")
public class NotificationNuevaOperacionSender extends NotificationSender {

    @Autowired
    public NotificationNuevaOperacionSender(String magicBellApiKey, String magicBellApiSecret, String clientUrl) {
        super(magicBellApiKey, magicBellApiSecret);
    }

    @Override
    public Notification doBuild(String title, Object helper, String actionUrl, String user) {
        Operacion operacion = (Operacion) helper;
        Long nroOperacion = operacion.getNroOperacion();
        String content = "¡Tu compra con N° " + nroOperacion + " se registró con éxito! Recordá completar\n" +
                "el pago si aún no lo hiciste.";

        return Notification.builder()
                .title(title)
                .content(content)
                .category(NotificationConstants.CATEGORY_NEW_MESSAGE)
                .actionUrl(actionUrl)
                .user(user).build();
    }
}
