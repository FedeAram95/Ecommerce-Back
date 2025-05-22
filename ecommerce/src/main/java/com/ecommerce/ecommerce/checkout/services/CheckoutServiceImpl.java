package com.ecommerce.ecommerce.checkout.services;

import java.util.Date;

import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.ecommerce.checkout.dto.CheckoutPayload;
import com.ecommerce.ecommerce.imagenes.entities.Imagen;
import com.ecommerce.ecommerce.operaciones.entities.EstadoOperacion;
import com.ecommerce.ecommerce.operaciones.entities.EventoOperacion;
import com.ecommerce.ecommerce.operaciones.entities.Operacion;
import com.ecommerce.ecommerce.operaciones.exceptions.OperacionException;
import com.ecommerce.ecommerce.operaciones.repositories.OperacionRepository;
import com.ecommerce.ecommerce.operaciones.statemachine.StateMachineService;
import com.ecommerce.ecommerce.pagos.entities.MedioPagoEnum;
import com.ecommerce.ecommerce.pagos.factory.OperacionPagoInfo;
import com.ecommerce.ecommerce.pagos.mapper.OperacionPagoMapper;
import com.ecommerce.ecommerce.pagos.services.strategy.PagoStrategy;
import com.ecommerce.ecommerce.pagos.services.strategy.PagoStrategyFactory;
import com.ecommerce.ecommerce.pagos.services.strategy.PagoStrategyName;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class CheckoutServiceImpl implements CheckoutService {

    private final OperacionRepository operacionRepository;

    private final PagoStrategyFactory pagoStrategyFactory;
    private final StateMachineService stateMachineService;
    private final OperacionPagoMapper operacionPagoMapper;

    @Transactional
    @Override
    public OperacionPagoInfo iniciarCheckout(Operacion operacion) {
        PagoStrategy pagoStrategy = this.getPagoStrategy(operacion.getMedioPago().getNombre());
        return pagoStrategy != null ? pagoStrategy.crearPago(operacion) : null;
    }

    @Transactional
    @Override
    public OperacionPagoInfo ejecutarCheckoutSuccess(CheckoutPayload checkoutPayload) {
        Operacion operacion = this.getOperacion(checkoutPayload.getNroOperacion());

        // Obtenemos esta fecha para asignarsela luego de completar el pago, para
        // que no se pierda al momento de crear el nuevo objeto de pago (son objetos distintos).
        Date fechaCreacionPago = operacion.getPago().getFechaCreacion();
        // Obtenemos el comprobante, por si existe.
        Imagen comprobante = null;
        if (operacion.getPago().getComprobante() != null) comprobante = operacion.getPago().getComprobante();

        // Delegamos el completar pago al strategy correspondiente. Cada pago es responsable de las transiciones a
        // estados correspondientes, ya que depende de la estrategia de pago.
        PagoStrategy pagoStrategy = this.getPagoStrategy(operacion.getMedioPago().getNombre());
        OperacionPagoInfo pagoInfo = pagoStrategy != null ? pagoStrategy
                .completarPago(operacion, checkoutPayload.getPaymentId(), checkoutPayload.getPreferenceId()) : null;
        operacion.setPago(this.operacionPagoMapper.mapToOperacionPago(pagoInfo));

        // Seteamos la fecha de creación guardada para no perder referencia
        operacion.getPago().setFechaCreacion(fechaCreacionPago);
        // Seteamos la fecha de pago al momento actual
        operacion.getPago().setFechaPagado(new Date());
        if (comprobante != null) operacion.getPago().setComprobante(comprobante);

        // Por último, guardamos la operación actualizada y devolvemos el objeto con la info del pago (DTO).
        this.operacionRepository.save(operacion);
        return pagoInfo;
    }

    @Transactional
    @Override
    public void ejecutarCheckoutFailure(CheckoutPayload checkoutPayload) {
        Operacion operacion = this.getOperacion(checkoutPayload.getNroOperacion());

        StateMachine<EstadoOperacion, EventoOperacion> sm = this.stateMachineService.build(operacion.getNroOperacion());

        sm.getExtendedState().getVariables().put("operacion", operacion);
        sm.getExtendedState().getVariables().put("useremail", operacion.getCliente().getEmail());

        log.info("Pago rechazado");

        this.stateMachineService.enviarEvento(operacion.getNroOperacion(), sm, EventoOperacion.RECHAZAR_PAGO);
    }

    private Operacion getOperacion(Long nroOperacion) {
        return this.operacionRepository.findById(nroOperacion)
                .orElseThrow(() -> new OperacionException("No existe la operación con n°: " + nroOperacion));
    }

    private PagoStrategy getPagoStrategy(MedioPagoEnum medioPagoNombre) {
        PagoStrategy pagoStrategy;

        if (medioPagoNombre.equals(MedioPagoEnum.EFECTIVO))
            pagoStrategy = this.pagoStrategyFactory.get(String.valueOf(PagoStrategyName.cashStrategy));
        else if (medioPagoNombre.equals(MedioPagoEnum.PAYPAL))
            pagoStrategy = this.pagoStrategyFactory.get(String.valueOf(PagoStrategyName.payPalStrategy));
        else if (medioPagoNombre.equals(MedioPagoEnum.MERCADO_PAGO))
            pagoStrategy = this.pagoStrategyFactory.get(String.valueOf(PagoStrategyName.mercadoPagoStrategy));
        else if (medioPagoNombre.equals(MedioPagoEnum.TRANSFERENCIA_BANCARIA))
            pagoStrategy = this.pagoStrategyFactory.get(String.valueOf(PagoStrategyName.transferenciaBancariaStrategy));
        else pagoStrategy = null;

        return pagoStrategy;
    }
}
