package com.ecommerce.ecommerce.compras.services;

import com.ecommerce.ecommerce.operaciones.entities.Operacion;

/**
 * Servicio para anular una compra por parte del usuario final. Distinto a servicios
 * para anular operación como admin, o por el paso del tiempo (automático).
 */
public interface AnularCompraService {

    /**
     * Método que anula compras realizadas en efectivo/transferencia bancaria que aun
     * no fueron retiradas/confirmadas. Solo se pueden anular operaciones (compras) propias
     * del cliente en sesión.
     * @param nroOperacion Long numero de operacion a anular.
     * @return {@link Operacion} anulada.
     */
    Operacion anular(Long nroOperacion);

}
