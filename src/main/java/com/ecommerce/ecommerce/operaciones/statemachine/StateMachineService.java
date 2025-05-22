package com.ecommerce.ecommerce.operaciones.statemachine;

import org.springframework.statemachine.StateMachine;

import com.ecommerce.ecommerce.operaciones.entities.EstadoOperacion;
import com.ecommerce.ecommerce.operaciones.entities.EventoOperacion;

public interface StateMachineService {

    StateMachine<EstadoOperacion, EventoOperacion> build(Long nroOperacion);

    void enviarEvento(Long nroOperacion, StateMachine<EstadoOperacion, EventoOperacion> sm, EventoOperacion evento);
}
