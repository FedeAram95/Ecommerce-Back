package com.ecommerce.ecommerce.compras.services;

import com.ecommerce.ecommerce.clientes.entities.Cliente;
import com.ecommerce.ecommerce.operaciones.entities.Operacion;
import com.ecommerce.ecommerce.operaciones.exceptions.OperacionException;

/**
 * Encuentra una {@link Operacion} (compra) para un {@link Cliente} requerido.
 */
public interface EncontradorCompraCliente {

    /**
     * Método que se encarga de cumplir la responsabilidad de encontrar una operación
     * para un cliente puntual.
     * @param nroOperacion Long numero de operación a encontrar.
     * @param cliente {@link Cliente} a quien debe pertenecer la operación.
     * @return {@link Operacion} del cliente requerido.
     */
    Operacion encontrarCompraCliente(Long nroOperacion, Cliente cliente) throws OperacionException;

}
