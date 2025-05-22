package com.ecommerce.ecommerce.pagos.services.bancaria;

import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.ecommerce.pagos.entities.OperacionPago;

/**
 * Se encarga de registrar el comprobante de pago para una {@link OperacionPago}.
 */
public interface ComprobantePagoRegistrador {

    /**
     * Registra y guarda el comprobante de pago para un pago requerido.
     * @param comprobante {@link MultipartFile} archivo del comprobante a subir.
     * @param pago {@link OperacionPago} a vincular el comprobante.
     * @return {@link OperacionPago} actualizado, con el comprobante.
     */
    OperacionPago registrarComprobantePago(MultipartFile comprobante, OperacionPago pago);
}
