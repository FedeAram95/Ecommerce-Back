package com.ecommerce.ecommerce.operaciones.notifications.services;

import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.notificaciones.dto.Notification;
import com.ecommerce.ecommerce.notificaciones.dto.NotificationConstants;
import com.ecommerce.ecommerce.notificaciones.services.NotificationSender;
import com.ecommerce.ecommerce.operaciones.entities.Operacion;

@Service(value = "nuevoComprobanteSubidoSender")
public class NotificationNuevoComprobanteSubidoSender extends NotificationSender {

    public NotificationNuevoComprobanteSubidoSender(String magicBellApiKey, String magicBellApiSecret) {
        super(magicBellApiKey, magicBellApiSecret);
    }

    @Override
    public Notification doBuild(String title, Object helper, String actionUrl, String user) {
        Operacion operacion = (Operacion) helper;
        Long nroOperacion = operacion.getNroOperacion();

        String cliente = operacion.getCliente().getApellido() + ", " +
                operacion.getCliente().getNombre();
        String builderContent = "Se registró un nuevo comprobante de pago para la operación N° " +
                nroOperacion + " del cliente " + cliente;
        return Notification.builder()
                .title(title)
                .content(builderContent)
                .category(NotificationConstants.CATEGORY_NEW_MESSAGE)
                .actionUrl(actionUrl)
                .user(user).build();
    }
}
