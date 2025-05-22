package com.ecommerce.ecommerce.pagos.services.bancaria;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.pagos.config.ExpiracionPagoConstants;
import com.ecommerce.ecommerce.pagos.services.GeneradorExpiracionDate;

/**
 * Implementación para generar expiration date para el pago con transferencia bancaria (tb).
 */
@Service
@Qualifier("tb")
public class GeneradorExpiracionDateBanc implements GeneradorExpiracionDate {

    @Override
    public Date expirationDate() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, ExpiracionPagoConstants.EXPIRACION_TB_DATE);

        date = calendar.getTime();
        return date;
    }
}
