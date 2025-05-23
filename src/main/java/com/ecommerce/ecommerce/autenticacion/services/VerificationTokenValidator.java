package com.ecommerce.ecommerce.autenticacion.services;

import com.ecommerce.ecommerce.autenticacion.entities.VerificationToken;
import com.ecommerce.ecommerce.utils.scheduled.Programable;

/**
 * Servicio que valida, y elimina, en caso de ser necesario, los tokens de validación
 * para el registro de usuarios, a través de su atributo "expiraEn".
 */
public interface VerificationTokenValidator extends Programable {

    /**
     * Valida que un {@link VerificationToken} haya expirado o no.
     * @param verificationToken a validar.
     * @return boolean.
     */
    boolean expired(VerificationToken verificationToken);

    /**
     * Elimina de la base de datos aquel {@link VerificationToken} que se requiera.
     * @param verificationToken a eliminar.
     */
    void delete(VerificationToken verificationToken);

}
