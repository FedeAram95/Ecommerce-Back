package com.ecommerce.ecommerce.pagos.services.bancaria;

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
import com.ecommerce.ecommerce.operaciones.exceptions.OperacionException;
import com.ecommerce.ecommerce.operaciones.statemachine.StateMachineService;
import com.ecommerce.ecommerce.pagos.cuentasbancarias.exceptions.CuentaBancariaException;
import com.ecommerce.ecommerce.pagos.cuentasbancarias.facade.CuentaBancariaResponse;
import com.ecommerce.ecommerce.pagos.cuentasbancarias.services.find.CuentaBancariaFinderService;
import com.ecommerce.ecommerce.pagos.dto.AmountPayload;
import com.ecommerce.ecommerce.pagos.dto.PayerPayload;
import com.ecommerce.ecommerce.pagos.exceptions.PaymentException;
import com.ecommerce.ecommerce.pagos.factory.OperacionPagoInfo;
import com.ecommerce.ecommerce.pagos.factory.OperacionPagoInfoFactory;
import com.ecommerce.ecommerce.pagos.services.GeneradorExpiracionDate;
import com.ecommerce.ecommerce.pagos.services.strategy.PagoStrategy;

import lombok.extern.slf4j.Slf4j;

/**
 * Implementación de {@link PagoStrategy} para los pagos con transferencia bancaria.
 */
@Service
@Slf4j
public class TransferenciaBancariaStrategy implements PagoStrategy {

    private final GeneradorExpiracionDate generadorExpiracionDate;
    private final StateMachineService stateMachineService;

    private final CuentaBancariaFinderService cuentaBancariaFinderService;

    @Autowired
    public TransferenciaBancariaStrategy(@Qualifier("tb") GeneradorExpiracionDate generadorExpiracionDate,
                                         StateMachineService stateMachineService,
                                         CuentaBancariaFinderService cuentaBancariaFinderService) {
        this.generadorExpiracionDate = generadorExpiracionDate;
        this.stateMachineService = stateMachineService;
        this.cuentaBancariaFinderService = cuentaBancariaFinderService;
    }

    @Override
    public OperacionPagoInfo crearPago(Operacion operacion) {
        Map<String, Object> atributosPago = new HashMap<>();

        Date expirationDate = this.generadorExpiracionDate.expirationDate();

        CuentaBancariaResponse cuentaBancaria;
        try {
            cuentaBancaria = this.cuentaBancariaFinderService.findPrincipal();
        } catch (CuentaBancariaException e) {
            throw new OperacionException("No se pudo procesar la compra. No existe ninguna cuenta bancaria " +
                    "cargada por administradores.");
        }
        Map<String, Object> infoCuenta = Map.of(
                "identificador", cuentaBancaria.getNroCuenta(),
                "alias", cuentaBancaria.getAlias(),
                "titular", cuentaBancaria.getTitular(),
                "banco", cuentaBancaria.getBanco(),
                "CA", cuentaBancaria.getCa(),
                "email", cuentaBancaria.getEmail(),
                "pais", cuentaBancaria.getPais());

        atributosPago.put("pagoId", UUID.randomUUID().toString());
        atributosPago.put("info", infoCuenta);
        atributosPago.put("nroOperacion", operacion.getNroOperacion());
        atributosPago.put("estado", "CREADO");
        atributosPago.put("expiraEn", expirationDate);
        atributosPago.put("monto", null);
        atributosPago.put("pagador", null);

        log.info("Pago creado con éxito");

        this.transitarEstado(operacion, EventoOperacion.REGISTRAR_RESERVA);

        return OperacionPagoInfoFactory
                .getOperacionPagoInfo(String.valueOf(operacion.getMedioPago().getNombre()), atributosPago);
    }

    @Override
    public OperacionPagoInfo completarPago(Operacion operacion, String paymentId, String preferenceId) {
        Map<String, Object> atributosPago = new HashMap<>();

        // Validamos la consistencia de datos de la operación con su pago
        if (!operacion.getPago().getId().equals(paymentId))
            throw new PaymentException("Inconsistencia con los datos del pago");

        // Si el pago ya fue efectuado, tiramos excepción
        if (operacion.getPago().getStatus().equalsIgnoreCase("COMPLETADO"))
            throw new PaymentException("El pago para esta operación ya ha sido completado");

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

        atributosPago.put("pagoId", operacion.getPago().getId());
        atributosPago.put("nroOperacion", operacion.getNroOperacion());
        atributosPago.put("estado", "COMPLETADO");
        atributosPago.put("monto", amount);
        atributosPago.put("pagador", payer);

        log.info("paymentId: " + paymentId);

        this.transitarEstado(operacion, EventoOperacion.CONFIRMAR_PAGO);

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
