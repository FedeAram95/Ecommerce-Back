package com.ecommerce.ecommerce.autenticacion.services;

import com.ecommerce.ecommerce.autenticacion.dto.CambiarPasswordRequest;
import com.ecommerce.ecommerce.autenticacion.entities.Usuario;

public interface PasswordService {

    /**
     * Cambiar la contraseña del usuario con la sesión actual.
     * @param passwordRequest
     */
    Usuario cambiarPassword(CambiarPasswordRequest passwordRequest);

    Usuario cambiarPassword(String token, CambiarPasswordRequest cambiarPasswordRequest);

    void recuperarPassword(String userEmail);
}
