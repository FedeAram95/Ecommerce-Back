package com.ecommerce.ecommerce.pagos.services.strategy;

/**
 * Factory que se encarga de crear y devolver el PagoStrategy requerido por el servicio
 * cliente que lo necesite.
 */
public interface PagoStrategyFactory {

    PagoStrategy get(String type);
}
