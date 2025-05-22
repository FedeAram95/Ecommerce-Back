package com.ecommerce.ecommerce.autenticacion.dto;

import java.util.Date;

import com.ecommerce.ecommerce.autenticacion.entities.AuthProvider;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DataTransferObject del Usuario --> No mostrar información sensible (aunque encriptada)
 * a los administradores del sistema.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDTO {

    private Long id;
    private String email;
    private boolean enabled;
    private Date fechaCreacion;
    private AuthProvider authProvider;
    private String providerId;
    private String rol;
}
