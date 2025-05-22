package com.ecommerce.ecommerce.operaciones.notifications.services;

import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.notificaciones.dto.Notification;
import com.ecommerce.ecommerce.notificaciones.dto.NotificationConstants;
import com.ecommerce.ecommerce.notificaciones.services.NotificationSender;
import com.ecommerce.ecommerce.operaciones.entities.Operacion;

/**
 * Notificación de devolución de una operación enviada al admin.
 */
@Service(value = "nuevaDevolucionSender")
public class NotificationNuevaDevolucionSender extends NotificationSender {

    public NotificationNuevaDevolucionSender(String magicBellApiKey, String magicBellApiSecret) {
        super(magicBellApiKey, magicBellApiSecret);
    }

    @Override
    public Notification doBuild(String title, Object helper, String actionUrl, String user) {
        Operacion operacion = (Operacion) helper;
        Long nroOperacion = operacion.getNroOperacion();
        String cliente = operacion.getCliente().getApellido() + ", " +
                operacion.getCliente().getNombre();
        String content = "El cliente " + cliente + " comenzó el proceso de devolución para la " +
                "operación N° " + nroOperacion;

        return Notification.builder()
                .title(title)
                .content(content)
                .category(NotificationConstants.CATEGORY_NEW_MESSAGE)
                .actionUrl(actionUrl)
                .user(user).build();
    }
}
