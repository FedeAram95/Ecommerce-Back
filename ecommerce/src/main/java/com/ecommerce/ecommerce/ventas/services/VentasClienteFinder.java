package com.ecommerce.ecommerce.ventas.services;

import java.util.List;

import com.ecommerce.ecommerce.ventas.dto.VentaPayload;

public interface VentasClienteFinder {
    /**
     * Obtiene listado de ventas del cliente requerido, a través de su id.
     * @param clienteId Long id del cliente.
     * @return {@link List<VentaPayload>} listado ventas.
     */
    List<VentaPayload> ventasCliente(Long clienteId);
}
