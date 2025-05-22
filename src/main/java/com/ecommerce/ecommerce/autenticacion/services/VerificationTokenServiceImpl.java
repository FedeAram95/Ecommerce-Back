package com.ecommerce.ecommerce.autenticacion.services;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.ecommerce.autenticacion.entities.Usuario;
import com.ecommerce.ecommerce.autenticacion.entities.VerificationToken;
import com.ecommerce.ecommerce.autenticacion.exceptions.AutenticacionException;
import com.ecommerce.ecommerce.autenticacion.repositories.VerificationTokenRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VerificationTokenServiceImpl implements VerificationTokenService {

    private final VerificationTokenRepository verificationTokenRepository;

    @Transactional
    @Override
    public String generarVerificationToken(Usuario usuario) {
        String token = UUID.randomUUID().toString();

        VerificationToken verificationToken = VerificationToken.builder()
                .token(token)
                .usuario(usuario)
                .expiraEn(this.fechaExpiracion())
                .build();

        this.verificationTokenRepository.save(verificationToken);
        return token;
    }


    @Override
    public VerificationToken getVerificationToken(String token) {
        return this.verificationTokenRepository.findByToken(token)
                .orElseThrow(() -> new AutenticacionException("Token inválido."));
    }

    @Transactional
    @Override
    public void delete(VerificationToken verificationToken) {
        this.verificationTokenRepository.delete(verificationToken);
    }

    /**
     * Método que calcula la fecha de expiración. Por defecto, se añade 1 (UN) día a la
     * fecha actual.
     * @return {@link Date} de expiración.
     */
    private Date fechaExpiracion() {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, 60);
        date = calendar.getTime();
        return date;
    }
}
