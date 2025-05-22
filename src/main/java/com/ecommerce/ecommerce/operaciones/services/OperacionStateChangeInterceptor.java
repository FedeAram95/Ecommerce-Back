package com.ecommerce.ecommerce.operaciones.services;

import java.util.Optional;

import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

import com.ecommerce.ecommerce.operaciones.entities.EstadoOperacion;
import com.ecommerce.ecommerce.operaciones.entities.EventoOperacion;
import com.ecommerce.ecommerce.operaciones.entities.Operacion;
import com.ecommerce.ecommerce.operaciones.exceptions.OperacionException;
import com.ecommerce.ecommerce.operaciones.repositories.OperacionRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class OperacionStateChangeInterceptor extends StateMachineInterceptorAdapter<EstadoOperacion, EventoOperacion> {

    private final OperacionRepository operacionRepository;

    @Override
    public void preStateChange(State<EstadoOperacion, EventoOperacion> state, Message<EventoOperacion> message,
                               Transition<EstadoOperacion, EventoOperacion> transition, StateMachine<EstadoOperacion, EventoOperacion> stateMachine) {

        Optional.ofNullable(message).ifPresent(msg -> {
            Optional.ofNullable(Long.class.cast(msg.getHeaders().getOrDefault(OperacionServiceImpl.NRO_OPERACION_HEADER, -1L)))
                    .ifPresent(nroOperacion -> {
                        Operacion operacion = this.operacionRepository.findById(nroOperacion)
                                .orElseThrow(() -> new OperacionException("No existe la operacion con id: " + nroOperacion));
                        operacion.setEstado(state.getId());
                        this.operacionRepository.save(operacion);
                    });
        });
    }
}
