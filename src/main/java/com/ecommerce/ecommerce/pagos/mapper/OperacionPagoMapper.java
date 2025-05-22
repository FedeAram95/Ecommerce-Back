package com.ecommerce.ecommerce.pagos.mapper;

import com.ecommerce.ecommerce.pagos.entities.OperacionPago;
import com.ecommerce.ecommerce.pagos.factory.OperacionPagoInfo;

/**
 * Esta clase se encarga de realizar el mapeo entre {@link OperacionPagoInfo}, que contiene
 * los datos al crear el pago; y {@link OperacionPago}, que es la entidad que persiste en la
 * base de datos, asociada a una operación.
 */

public interface OperacionPagoMapper {

    OperacionPago mapToOperacionPago(OperacionPagoInfo pagoInfo);

}
