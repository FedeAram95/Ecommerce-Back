package com.ecommerce.ecommerce.operaciones.notifications.services;

import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.notificaciones.dto.Notification;
import com.ecommerce.ecommerce.notificaciones.dto.NotificationConstants;
import com.ecommerce.ecommerce.notificaciones.services.NotificationSender;
import com.ecommerce.ecommerce.operaciones.entities.Operacion;

@Service(value = "operacionEnviadaSender")
public class NotificationOperacionEnviadaSender extends NotificationSender {

    public NotificationOperacionEnviadaSender(String magicBellApiKey, String magicBellApiSecret) {
        super(magicBellApiKey, magicBellApiSecret);
    }

    @Override
    public Notification doBuild(String title, Object helper, String actionUrl, String user) {
        Operacion operacion = (Operacion) helper;
        Long nroOperacion = operacion.getNroOperacion();
        String content = "¡Los items de tu compra con N° " + nroOperacion + " ya están en camino!";

        return Notification.builder()
                .title(title)
                .content(content)
                .category(NotificationConstants.CATEGORY_NEW_MESSAGE)
                .actionUrl(actionUrl)
                .user(user).build();
    }
}
