package com.ecommerce.ecommerce.autenticacion.services;

import com.ecommerce.ecommerce.autenticacion.entities.Usuario;
import com.ecommerce.ecommerce.autenticacion.entities.VerificationToken;

/**
 * Servicio para generar tokens de validación para completar el registro de usuarios.
 */
public interface VerificationTokenService {

    String generarVerificationToken(Usuario usuario);

    VerificationToken getVerificationToken(String token);

    void delete(VerificationToken verificationToken);
}
