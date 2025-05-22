package com.ecommerce.ecommerce.ventas.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.ecommerce.clientes.entities.Cliente;
import com.ecommerce.ecommerce.clientes.repositories.ClienteRepository;
import com.ecommerce.ecommerce.operaciones.entities.Operacion;
import com.ecommerce.ecommerce.operaciones.repositories.OperacionRepository;
import com.ecommerce.ecommerce.perfiles.exceptions.PerfilesException;
import com.ecommerce.ecommerce.ventas.dto.VentaPayload;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VentasClienteFinderImpl implements VentasClienteFinder {

    private final ClienteRepository clienteRepository;
    private final OperacionRepository operacionRepository;


    @Override
    public List<VentaPayload> ventasCliente(Long clienteId) {
        Cliente cliente = this.clienteRepository.findById(clienteId)
                .orElseThrow(() -> new PerfilesException("No existe cliente con id: " + clienteId));

        List<Operacion> ventasCliente = this.operacionRepository.findAllByClienteOrderByFechaOperacionAsc(cliente);
        return ventasCliente
                .stream()
                .map(this::mapToVenta)
                .collect(Collectors.toList());
    }

    private VentaPayload mapToVenta(Operacion operacion) {
        return VentaPayload.builder()
                .nroOperacion(operacion.getNroOperacion())
                .fechaOperacion(operacion.getFechaOperacion())
                .fechaEnvio(operacion.getFechaEnvio())
                .fechaEntrega(operacion.getFechaEntrega())
                .estado(operacion.getEstado())
                .cliente(operacion.getCliente())
                .direccionEnvio(operacion.getDireccionEnvio())
                .medioPago(operacion.getMedioPago())
                .items(operacion.getItems())
                .total(operacion.getTotal()).build();
    }
}
