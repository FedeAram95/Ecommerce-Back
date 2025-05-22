package com.ecommerce.ecommerce.autenticacion.dto;

import com.ecommerce.ecommerce.clientes.entities.Cliente;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {

    private String email;
    private String password;
    private Cliente cliente;
}
