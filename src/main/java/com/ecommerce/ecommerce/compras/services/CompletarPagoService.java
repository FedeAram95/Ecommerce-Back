package com.ecommerce.ecommerce.compras.services;

import com.ecommerce.ecommerce.pagos.entities.OperacionPago;

/**
 * Servicio para completar el pago de una {@link com.ecommerce.ecommerce.operaciones.entities.Operacion}
 * (compra), por parte de un usuario cliente.
 */
public interface CompletarPagoService {

    /**
     * Completa el pago de una compra en particular de un usuario.
     * @param nroOperacion Long id de la operacion a completar su pago.
     * @return {@link OperacionPago} correspondiente a la compra, con los datos para completar el pago.
     */
    OperacionPago completarPago(Long nroOperacion);

}
