package com.ecommerce.ecommerce.pagos.services.cash;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.operaciones.entities.EstadoOperacion;
import com.ecommerce.ecommerce.operaciones.entities.EventoOperacion;
import com.ecommerce.ecommerce.operaciones.entities.Operacion;
import com.ecommerce.ecommerce.operaciones.statemachine.StateMachineService;
import com.ecommerce.ecommerce.pagos.dto.AmountPayload;
import com.ecommerce.ecommerce.pagos.dto.PayerPayload;
import com.ecommerce.ecommerce.pagos.exceptions.PaymentException;
import com.ecommerce.ecommerce.pagos.factory.OperacionPagoInfo;
import com.ecommerce.ecommerce.pagos.factory.OperacionPagoInfoFactory;
import com.ecommerce.ecommerce.pagos.services.GeneradorExpiracionDate;
import com.ecommerce.ecommerce.pagos.services.strategy.PagoStrategy;

import lombok.extern.slf4j.Slf4j;

/**
 * Implementación de {@link PagoStrategy} para los pagos en efectivo de la tienda online.
 */
@Service
@Slf4j
public class CashStrategy implements PagoStrategy {

    private final GeneradorExpiracionDate generadorExpiracionDate;
    private final StateMachineService stateMachineService;

    @Autowired
    public CashStrategy(@Qualifier("cash") GeneradorExpiracionDate generadorExpiracionDate, StateMachineService stateMachineService) {
        this.generadorExpiracionDate = generadorExpiracionDate;
        this.stateMachineService = stateMachineService;
    }

    @Override
    public OperacionPagoInfo crearPago(Operacion operacion) {
        Map<String, Object> atributosPago = new HashMap<>();

        Date expirationDate = this.generadorExpiracionDate.expirationDate();

        atributosPago.put("nroPago", UUID.randomUUID().toString());
        atributosPago.put("nroOperacion", operacion.getNroOperacion());
        atributosPago.put("estado", "CREADO");
        atributosPago.put("expiraEn", expirationDate);
        atributosPago.put("monto", null);
        atributosPago.put("pagador", null);

        log.info("Pago creado con éxito");

        // Lógica de transición de estado. Delegamos el comportamiento a la state machine.
        this.transitarEstado(operacion, EventoOperacion.REGISTRAR_RESERVA);

        return OperacionPagoInfoFactory
                .getOperacionPagoInfo(String.valueOf(operacion.getMedioPago().getNombre()), atributosPago);
    }

    @Override
    public OperacionPagoInfo completarPago(Operacion operacion, String pagoId, String preferenceId) {
        Map<String, Object> atributosPago = new HashMap<>();

        // Validamos la consistencia de datos de la operación con su pago
        if (!operacion.getPago().getId().equals(pagoId))
            throw new PaymentException("Inconsistencia con los datos del pago");

        // Si el pago ya fue efectuado, tiramos excepción
        if (operacion.getPago().getStatus().equalsIgnoreCase("COMPLETADO"))
            throw new PaymentException("El pago para esta operación ya fue completado");

        PayerPayload payer = PayerPayload.builder()
                .payerId(String.valueOf(operacion.getCliente().getId()))
                .payerEmail(operacion.getCliente().getEmail())
                .payerFullName(operacion.getCliente().getNombre().concat(" ").concat(operacion.getCliente().getApellido()))
                .build();

        AmountPayload amount = AmountPayload.builder()
                .totalBruto(null)
                .totalNeto(String.valueOf(operacion.getTotal()))
                .fee(null)
                .build();

        atributosPago.put("nroPago", operacion.getPago().getId());
        atributosPago.put("nroOperacion", operacion.getNroOperacion());
        atributosPago.put("estado", "COMPLETADO");
        atributosPago.put("monto", amount);
        atributosPago.put("pagador", payer);

        log.info("preferenceId: " + preferenceId);
        log.info("paymentId: " + pagoId);

        // Transitamos al estado ENTREGADO, ya que el administrador marcó como pago completado, que indica la entrega
        // de los items en la tienda por pago en efectivo.
        this.transitarEstado(operacion, EventoOperacion.ENTREGAR);

        return OperacionPagoInfoFactory
                .getOperacionPagoInfo(String.valueOf(operacion.getMedioPago().getNombre()), atributosPago);
    }

    private void transitarEstado(Operacion operacion, EventoOperacion evento) {
        StateMachine<EstadoOperacion, EventoOperacion> sm = this.stateMachineService.build(operacion.getNroOperacion());
        sm.getExtendedState().getVariables().put("operacion", operacion);
        sm.getExtendedState().getVariables().put("useremail", operacion.getCliente().getEmail());
        this.stateMachineService.enviarEvento(operacion.getNroOperacion(), sm, evento);
    }
}
