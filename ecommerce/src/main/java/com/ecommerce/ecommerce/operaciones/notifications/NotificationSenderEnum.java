package com.ecommerce.ecommerce.operaciones.notifications;

/**
 * Contiene ENUMS que se utilizan para instanciar  las distintas implementaciones
 * de {@link com.ecommerce.ecommerce.notificaciones.services.NotificationSender}
 */
public enum NotificationSenderEnum {
    nuevaOperacionSender,
    pagoRegistradoSender,
    ventaSender,
    operacionEnviadaSender,
    operacionEntregadaSender,
    nuevaCompraCashSender,
    nuevaVentaCashSender,
    anularOperacionSender,
    cancelarOperacionSender,
    comprobarPagoSender,
    nuevoComprobanteSubidoSender,
    confirmarComprobanteSender,
    rechazarComprobanteSender,
    devolverOperacionSender,
    nuevaDevolucionSender,
    confirmarDevolucionSender
}
