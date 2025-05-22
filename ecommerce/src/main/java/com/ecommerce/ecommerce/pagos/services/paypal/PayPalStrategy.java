package com.ecommerce.ecommerce.pagos.services.paypal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.ecommerce.ecommerce.catalogo.skus.entities.Sku;
import com.ecommerce.ecommerce.operaciones.entities.DetalleOperacion;
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
import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.AmountBreakdown;
import com.paypal.orders.AmountWithBreakdown;
import com.paypal.orders.ApplicationContext;
import com.paypal.orders.Item;
import com.paypal.orders.LinkDescription;
import com.paypal.orders.Money;
import com.paypal.orders.Order;
import com.paypal.orders.OrderRequest;
import com.paypal.orders.OrdersCaptureRequest;
import com.paypal.orders.OrdersCreateRequest;
import com.paypal.orders.PurchaseUnitRequest;

import lombok.extern.slf4j.Slf4j;

/**
 * Implementación de {@link PagoStrategy} que realiza el pago conectandose con la API de PayPal, para crear la orden y
 * capturarla al completar el checkout.
 */
@Service
@Slf4j
public class PayPalStrategy implements PagoStrategy {

    private static final String CURRENCY = "USD";
    private static final String INTENT = "CAPTURE";

    private final GeneradorExpiracionDate generadorExpiracionDate;
    private final StateMachineService stateMachineService;

    private final PayPalHttpClient client;
    @Value("${endpoints.baseUrl}")
    private String baseUrl;
    @Autowired
    public PayPalStrategy(@Qualifier("pp") GeneradorExpiracionDate generadorExpiracionDate,
                          PayPalHttpClient client,
                          String baseUrl,
                          StateMachineService stateMachineService) {
        this.generadorExpiracionDate = generadorExpiracionDate;
        this.client = client;
        this.baseUrl = baseUrl;
        this.stateMachineService = stateMachineService;
    }

    @Override
    public OperacionPagoInfo crearPago(Operacion operacion) {
        // Creamos la request con el body de acuerdo a la operación.
        OrdersCreateRequest request = new OrdersCreateRequest().requestBody(this.buildOrderRequestBody(operacion));
        // Atributos para nuestra clase OperacionPagoInfo
        Map<String, Object> atributosPago = new HashMap<>();

        Date expirationDate = this.generadorExpiracionDate.expirationDate();

        try {
            Order order;
            HttpResponse<Order> response = this.client.execute(request);
            String approveUrl = null;

            order = response.result();

            for (LinkDescription link: order.links()) {
                if (link.rel().equals("approve")) {
                    approveUrl = link.href();
                    break;
                }
            }

            order.links().forEach(link -> log.info(link.rel() + " => " + link.method() + ":" + link.href()));

            // Completamos los datos para nuestra clase de pago.
            atributosPago.put("orderId", order.id());
            atributosPago.put("nroOperacion", operacion.getNroOperacion());
            atributosPago.put("status", order.status());
            atributosPago.put("expiresIn", expirationDate);
            atributosPago.put("approveUrl", approveUrl);
            atributosPago.put("amount", null);
            atributosPago.put("payer", null);
        } catch (IOException e) {
            throw new PaymentException("Error al crear con paypal la orden de pago: " +
                    e.getMessage());
        }

        log.info("Pago creado con éxito");
        return OperacionPagoInfoFactory
                .getOperacionPagoInfo(String.valueOf(operacion.getMedioPago().getNombre()), atributosPago);
    }

    @Override
    public OperacionPagoInfo completarPago(Operacion operacion, String orderId, String preferenceId) {

        // Si el pago ya fue efectuado, tiramos excepción
        if (operacion.getPago().getStatus().equalsIgnoreCase("completed"))
            throw new PaymentException("El pago para esta operación ya fue completado");

        if (!operacion.getPago().getId().equals(orderId))
            throw new PaymentException("El ID de la orden de PayPal no coincide con el ID del pago de la operación");

        OrderRequest orderRequest = new OrderRequest();
        OrdersCaptureRequest request = new OrdersCaptureRequest(orderId);
        request.requestBody(orderRequest);

        // Creamos mapa de atributos para nuestra clase de pago.
        Map<String, Object> atributosPago = new HashMap<>();
        Order order;
        try {
            HttpResponse<Order> response = this.client.execute(request);
            order = response.result();
        } catch (IOException e) {
            throw new PaymentException("Error al completar el pago con PayPal: " +
                    e.getMessage());
        }

        PayerPayload payer = PayerPayload.builder()
                .payerId(order.payer().payerId())
                .payerEmail(order.payer().email())
                .payerFullName(order.payer().name().givenName().concat(" ").concat(order.payer().name().surname()))
                .build();

        AmountPayload amount = AmountPayload.builder()
                .totalBruto(order.purchaseUnits().get(0).payments().captures().get(0)
                        .sellerReceivableBreakdown().grossAmount().value())
                .totalNeto(order.purchaseUnits().get(0).payments().captures().get(0)
                        .sellerReceivableBreakdown().netAmount().value())
                .fee(order.purchaseUnits().get(0).payments().captures().get(0)
                        .sellerReceivableBreakdown().paypalFee().value())
                .build();

        // Lógica transición de estados al completar el checkout (pago)
        this.transitarEstado(operacion);

        // Completamos los datos de los atributos para nuestro pago
        atributosPago.put("orderId", order.id());
        atributosPago.put("nroOperacion", operacion.getNroOperacion());
        atributosPago.put("status", order.status());
        atributosPago.put("payer", payer);
        atributosPago.put("amount", amount);

        log.info("Pago completado con éxito");
        return OperacionPagoInfoFactory
                .getOperacionPagoInfo(String.valueOf(operacion.getMedioPago().getNombre()), atributosPago);
    }

    /**
     * Método que se encarga de armar el body de la order, recibiendo la operación como parámetro para completar
     * los datos de la PayPal order.
     * @param operacion Operacion a crear pago.
     * @return OrderRequest creado.
     */
    private OrderRequest buildOrderRequestBody(Operacion operacion) {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.checkoutPaymentIntent(INTENT);

        // Creamos las URIs de redirección
        String targetCancelUrl = this.baseUrl.concat("/api/checkout/failure");
        String CANCEL_REDIRECT_URL = UriComponentsBuilder.fromUriString(targetCancelUrl)
                .queryParam("nroOperacion", operacion.getNroOperacion()).build().toString();

        String targetApprovedUri = this.baseUrl.concat("/api/checkout/success");
        String APPROVED_REDIRECT_URL = UriComponentsBuilder.fromUriString(targetApprovedUri)
                .queryParam("nroOperacion", operacion.getNroOperacion()).build().toString();

        ApplicationContext context = new ApplicationContext()
                .landingPage("BILLING")
                .cancelUrl(CANCEL_REDIRECT_URL).userAction("CANCEL")
                .returnUrl(APPROVED_REDIRECT_URL).userAction("CONTINUE");

        orderRequest.applicationContext(context);

        List<PurchaseUnitRequest> purchaseUnitRequests = new ArrayList<>();

        PurchaseUnitRequest unitRequest = new PurchaseUnitRequest();

        AmountWithBreakdown amount = new AmountWithBreakdown()
                .currencyCode(CURRENCY)
                .value(String.valueOf(operacion.getTotal()))
                .amountBreakdown(new AmountBreakdown()
                        .itemTotal(new Money().currencyCode(CURRENCY).value(String.valueOf(operacion.getTotal()))));

        List<Item> items = this.agregarItems(operacion);

        unitRequest.amountWithBreakdown(amount).items(items);

        purchaseUnitRequests.add(unitRequest);
        orderRequest.purchaseUnits(purchaseUnitRequests);

        return orderRequest;
    }

    private List<Item> agregarItems(Operacion operacion) {
        List<Item> items = new ArrayList<>();

        for (DetalleOperacion item: operacion.getItems()) {
            Sku sku = item.getSku();

            items.add(new Item()
                    .name(sku.getNombre())
                    .unitAmount(new Money().currencyCode(CURRENCY).value(String.valueOf(item.getPrecioVenta())))
                    .quantity(String.valueOf(item.getCantidad()))
                    .category("PHYSICAL_GOODS"));
        }

        return items;
    }

    private void transitarEstado(Operacion operacion) {
        StateMachine<EstadoOperacion, EventoOperacion> sm = this.stateMachineService.build(operacion.getNroOperacion());
        sm.getExtendedState().getVariables().put("operacion", operacion);
        sm.getExtendedState().getVariables().put("useremail", operacion.getCliente().getEmail());
        this.stateMachineService.enviarEvento(operacion.getNroOperacion(), sm, EventoOperacion.CONFIRMAR_PAGO);
    }
}
