package com.ecommerce.ecommerce.operaciones.notifications.services;

import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.notificaciones.dto.Notification;
import com.ecommerce.ecommerce.notificaciones.dto.NotificationConstants;
import com.ecommerce.ecommerce.notificaciones.services.NotificationSender;
import com.ecommerce.ecommerce.operaciones.entities.Operacion;
import com.ecommerce.ecommerce.pagos.config.ExpiracionPagoConstants;

/**
 * Notificación para el registro de una nueva operación por medio de pago efectivo o
 * transferencia bancaria.
 * <br>
 * Sender orientado a usuarios compradores.
 */
@Service(value = "nuevaCompraCashSender")
public class NotificationNuevaCompraCashSender extends NotificationSender {

    public NotificationNuevaCompraCashSender(String magicBellApiKey, String magicBellApiSecret) {
        super(magicBellApiKey, magicBellApiSecret);
    }

    @Override
    public Notification doBuild(String title, Object helper, String actionUrl, String user) {
        Operacion operacion = (Operacion) helper;
        Long nroOperacion = operacion.getNroOperacion();
        int maxDiasCash = ExpiracionPagoConstants.EXPIRACION_CASH_DATE;
        int maxDiasTB = ExpiracionPagoConstants.EXPIRACION_TB_DATE;
        String content = "¡Tu compra con N° " + nroOperacion + " se registró con éxito! \n" +
                "Límite para retirar en tienda: " + maxDiasCash + " días. \n" +
                "Límite para completar transferencia: " + maxDiasTB + " días.";

        return Notification.builder()
                .title(title)
                .content(content)
                .category(NotificationConstants.CATEGORY_NEW_MESSAGE)
                .actionUrl(actionUrl)
                .user(user).build();
    }
}
