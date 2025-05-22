package com.ecommerce.ecommerce.compras.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.ecommerce.clientes.entities.Cliente;
import com.ecommerce.ecommerce.operaciones.entities.Operacion;
import com.ecommerce.ecommerce.operaciones.exceptions.OperacionException;
import com.ecommerce.ecommerce.operaciones.repositories.OperacionRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EncontradorCompraClienteImpl implements EncontradorCompraCliente {

    private final OperacionRepository operacionRepository;

    @Transactional(readOnly = true)
    @Override
    public Operacion encontrarCompraCliente(Long nroOperacion, Cliente cliente) throws OperacionException {
        return this.operacionRepository.findByNroOperacionAndCliente(nroOperacion, cliente)
                .orElseThrow(() -> new OperacionException("No existe la operación requerida para el cliente " +
                        "seleccionado"));
    }
}
