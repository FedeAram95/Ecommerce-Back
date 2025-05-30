package com.ecommerce.ecommerce.autenticacion.services;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.ecommerce.autenticacion.entities.RefreshToken;
import com.ecommerce.ecommerce.autenticacion.repositories.RefreshTokenRepository;

/**
 * Servicio que elimina diariamente los refresh tokens que hayan expirado al momento
 * de la validación.
 */
@Service
public class RefreshTokenValidatorImpl implements RefreshTokenValidator {

    private final Logger log = LoggerFactory.getLogger(RefreshTokenValidatorImpl.class);
    private final RefreshTokenRepository refreshTokenRepository;

    @Autowired
    public RefreshTokenValidatorImpl(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }


    @Override
    public List<RefreshToken> findAllExpired() {
        return this.refreshTokenRepository.findAllByExpirationLessThan(new Date());
    }

    @Transactional
    @Override
    public void delete(RefreshToken refreshToken) {
        this.refreshTokenRepository.delete(refreshToken);
    }

    @Override
    @Scheduled(cron = "0 0 1 1/1 * ?")
    public void execute() {
        List<RefreshToken> expired = this.findAllExpired();

        for (RefreshToken actualRefreshToken: expired) {
            this.delete(actualRefreshToken);
            log.info("Eliminando refresh token...");
        }

        log.info("Total rt eliminados: " + expired.size());
    }
}
