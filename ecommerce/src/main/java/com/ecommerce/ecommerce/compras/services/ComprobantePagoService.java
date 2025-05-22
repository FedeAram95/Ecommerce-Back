package com.ecommerce.ecommerce.compras.services;

import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.ecommerce.compras.dto.CompraPayload;
import com.ecommerce.ecommerce.imagenes.entities.Imagen;

/**
 * Servicio que utiliza el usuario para registrar el comprobante de pago para una transferencia
 * bancaria, subiendo una imagen con dicho comprobante.
 */
public interface ComprobantePagoService {

    /**
     * Sube imagen de comprobante y la registra para el pago de la operación requerida.
     * @param comprobante {@link MultipartFile} archivo con la imagen del comprobante.
     * @param nroOperacion Long numero de operación del pago a comprobar.
     * @return {@link CompraPayload} datos actualizados de la compra (operacion).
     */
    CompraPayload subirComprobantePago(MultipartFile comprobante, Long nroOperacion);

    /**
     * Obtiene la imagen vinculada al pago de una compra (operación) del cliente actual.
     * @param nroOperacion Long numero de operacion a obtener la imagen de su comprobante de pago.
     * @return {@link Imagen} comprobante del pago.
     */
    Imagen obtenerImagenComprobantePago(Long nroOperacion);
}
