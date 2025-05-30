package com.ecommerce.ecommerce.pagos.services.strategy;

import com.ecommerce.ecommerce.operaciones.entities.Operacion;
import com.ecommerce.ecommerce.pagos.factory.OperacionPagoInfo;

/**
 * Servicio que se encarga de realizar los pagos de operaciones, según el medio de
 * pago elegido al momento de registrar la operación.
 */

public interface PagoStrategy {

    /**
     * Crea el pago con la información para completar el mismo, dependiendo del medio
     * de pago seleccionado.
     * @param operacion Operacion registrada a crear su pago.
     * @return OperacionPagoInfo con la información necesaria para completar el pago.
     */
    OperacionPagoInfo crearPago(Operacion operacion);

    /**
     * Toma el pago de la operación solicitada, y se completa el pago de la misma, en caso
     * de que el usuario lo desee así.
     * @param operacion operación a completar el pago.
     * @param paymentId String con el id del pago, que depende del medio de pago que se utilizó.
     * @param preferenceId String algunos medios de pago necesitan este ID para la validación de pago.
     * @return OperacionPagoInfo con la información del pago completado.
     */
    OperacionPagoInfo completarPago(Operacion operacion, String paymentId, String preferenceId);
}
