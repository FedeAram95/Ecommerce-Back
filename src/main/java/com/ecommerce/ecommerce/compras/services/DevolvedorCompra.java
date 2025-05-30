package com.ecommerce.ecommerce.compras.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.ecommerce.clientes.entities.Cliente;
import com.ecommerce.ecommerce.operaciones.entities.EstadoOperacion;
import com.ecommerce.ecommerce.operaciones.entities.EventoOperacion;
import com.ecommerce.ecommerce.operaciones.entities.Operacion;
import com.ecommerce.ecommerce.operaciones.exceptions.OperacionException;
import com.ecommerce.ecommerce.operaciones.statemachine.StateMachineService;
import com.ecommerce.ecommerce.perfiles.services.PerfilService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DevolvedorCompra implements DevolverCompraService {

    private final PerfilService perfilService;
    private final EncontradorCompraCliente encontradorCompraCliente;
    private final StateMachineService stateMachineService;

    @Transactional
    @Override
    public Operacion devolver(Long nroOperacion) {
        Cliente clienteActual = this.getClientePerfil();
        Operacion operacion = this.encontradorCompraCliente.encontrarCompraCliente(nroOperacion, clienteActual);

        List<EstadoOperacion> estadosValidos = new ArrayList<>();
        estadosValidos.add(EstadoOperacion.PAGO_CONFIRMADO);
        estadosValidos.add(EstadoOperacion.ENVIADO);
        estadosValidos.add(EstadoOperacion.ENTREGADO);

        if (!estadosValidos.contains(operacion.getEstado()))
            throw new OperacionException("No se puede devolver una operación que no se encuentre en los siguientes estados: " +
                    "PAGO_CONFIRMADO/ENVIADO/RECIBIDO");

        StateMachine<EstadoOperacion, EventoOperacion> sm = this.stateMachineService.build(nroOperacion);

        sm.getExtendedState().getVariables().put("operacion", operacion);
        sm.getExtendedState().getVariables().put("useremail", operacion.getCliente().getEmail());

        this.stateMachineService.enviarEvento(nroOperacion, sm, EventoOperacion.DEVOLVER);
        return operacion;
    }

    /**
     * Método que devuelve el {@link Cliente} del perfil actual, es decir, el perfil del usuario que tiene
     * sesión actualmente.
     * @return {@link Cliente}.
     */
    private Cliente getClientePerfil() {
        return this.perfilService.obtenerDatosCliente();
    }
}
