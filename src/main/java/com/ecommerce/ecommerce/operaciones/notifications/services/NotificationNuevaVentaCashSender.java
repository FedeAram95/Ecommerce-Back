package com.ecommerce.ecommerce.operaciones.notifications.services;

import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.notificaciones.dto.Notification;
import com.ecommerce.ecommerce.notificaciones.dto.NotificationConstants;
import com.ecommerce.ecommerce.notificaciones.services.NotificationSender;
import com.ecommerce.ecommerce.operaciones.entities.Operacion;

/**
 * Notificación para el registro de una nueva operación por medio de pago efectivo o
 * transferencia bancaria.
 * <br>
 * Este sender esta orientado a los administradores.
 */
@Service(value = "nuevaVentaCashSender")
public class NotificationNuevaVentaCashSender extends NotificationSender {

    public NotificationNuevaVentaCashSender(String magicBellApiKey, String magicBellApiSecret) {
        super(magicBellApiKey, magicBellApiSecret);
    }

    @Override
    public Notification doBuild(String title, Object helper, String actionUrl, String user) {
        Operacion operacion = (Operacion) helper;
        Long nroOperacion = operacion.getNroOperacion();
        String content = "Nueva venta con N° " + nroOperacion + " registrada en la tienda con medio de pago efectivo/transferencia bancaria.";

        return Notification.builder()
                .title(title)
                .content(content)
                .category(NotificationConstants.CATEGORY_NEW_MESSAGE)
                .actionUrl(actionUrl)
                .user(user).build();
    }
}
