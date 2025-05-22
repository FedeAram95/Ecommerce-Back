package com.ecommerce.ecommerce.pagos.services.mercadopago;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.pagos.config.ExpiracionPagoConstants;
import com.ecommerce.ecommerce.pagos.services.GeneradorExpiracionDate;

/**
 * Implementación para generar expiration date para el pago con la API de Mercado Pago (mercadoPago).
 */
@Service
@Qualifier("mp") 
public class GeneradorExpiracionDateMP implements GeneradorExpiracionDate {

    @Override
    public Date expirationDate() {
        Date dt = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dt);
        calendar.add(Calendar.DATE, ExpiracionPagoConstants.EXPIRACION_MP_DATE);

        dt = calendar.getTime();
        return dt;
    }
}
