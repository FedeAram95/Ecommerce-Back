package com.ecommerce.ecommerce.pagos.services;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.ecommerce.checkout.services.CheckoutService;
import com.ecommerce.ecommerce.operaciones.entities.Operacion;
import com.ecommerce.ecommerce.pagos.entities.OperacionPago;
import com.ecommerce.ecommerce.pagos.mapper.OperacionPagoMapper;
import com.ecommerce.ecommerce.pagos.repositories.OperacionPagoRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PagoHandlerImpl implements PagoHandler {

    private final CheckoutService checkoutService;
    private final OperacionPagoMapper operacionPagoMapper;
    private final OperacionPagoRepository pagoRepository;

    @Transactional
    @Override
    public OperacionPago procesarPago(Operacion operacion) {
        OperacionPago pagoManejado;
        OperacionPago pagoActual = operacion.getPago();
        if (pagoActual.isExpired()) {
            this.pagoRepository.delete(pagoActual);
            pagoManejado = this.operacionPagoMapper.mapToOperacionPago(
                    this.checkoutService.iniciarCheckout(operacion));
            pagoManejado.setFechaCreacion(new Date());
            return pagoManejado;
        }

        return pagoActual;
    }
}
