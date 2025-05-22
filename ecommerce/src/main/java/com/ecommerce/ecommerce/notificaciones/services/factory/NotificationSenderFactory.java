package com.ecommerce.ecommerce.notificaciones.services.factory;

import com.ecommerce.ecommerce.notificaciones.services.NotificationSender;

public interface NotificationSenderFactory {

    NotificationSender get(String type);

}
