package com.ecommerce.ecommerce.clientes.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.ecommerce.clientes.entities.Cliente;
import com.ecommerce.ecommerce.clientes.entities.Direccion;
import com.ecommerce.ecommerce.clientes.exceptions.ClienteException;
import com.ecommerce.ecommerce.clientes.repositories.ClienteRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    @Override
    public Cliente obtenerCliente(Long id) {
        return this.findById(id);
    }

    @Override
    public Cliente crear(Cliente cliente) {
        Cliente nuevoCliente = Cliente.builder()
                .dni(cliente.getDni())
                .nombre(cliente.getNombre())
                .apellido(cliente.getApellido())
                .fechaNacimiento(cliente.getFechaNacimiento())
                .telefono(cliente.getTelefono())
                .email(cliente.getEmail())
                .direccion(cliente.getDireccion())
                .build();

        return this.save(nuevoCliente);
    }

    @Override
    public Cliente actualizar(Cliente clienteActualizado, Long clienteId) {
        Cliente clienteActual = this.obtenerCliente(clienteId);

        clienteActual.setNombre(clienteActualizado.getNombre());
        clienteActual.setApellido(clienteActualizado.getApellido());
        clienteActual.setDni(clienteActualizado.getDni());
        clienteActual.setTelefono(clienteActualizado.getTelefono());
        clienteActual.setDireccion(clienteActualizado.getDireccion());
        clienteActual.setFechaNacimiento(clienteActualizado.getFechaNacimiento());

        return this.save(clienteActual);
    }

    @Override
    public Cliente actualizarDireccion(Cliente cliente, Direccion direccion) {
        cliente.setDireccion(direccion);
        return this.save(cliente);
    }


    @Override
    public List<Cliente> findAll() {
        return this.clienteRepository.findAll();
    }


    @Override
    public Cliente findById(Long aLong) {
        return this.clienteRepository.findById(aLong)
                .orElseThrow(() -> new ClienteException("No existe el cliente con id: " + aLong));
    }

    @Transactional
    @Override
    public Cliente save(Cliente object) {
        return this.clienteRepository.save(object);
    }

    @Override
    public void delete(Cliente object) {
        System.out.println("Not implemented...");
    }

    @Override
    public void deleteById(Long aLong) {
        System.out.println("Not implemented...");
    }
}
