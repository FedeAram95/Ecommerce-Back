package com.ecommerce.ecommerce.localizaciones.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.ecommerce.localizaciones.entities.Pais;
import com.ecommerce.ecommerce.localizaciones.exceptions.LocalizationException;
import com.ecommerce.ecommerce.localizaciones.repositories.PaisRepository;

/**
 * Implementación utilizando JPA de {@link PaisDSGateway}.
 */
@Service
public class JpaPais implements PaisDSGateway {

    private final PaisRepository paisRepository;

    @Autowired
    public JpaPais(PaisRepository paisRepository) {
        this.paisRepository = paisRepository;
    }


    @Override
    public List<Pais> findAll() {
        return this.paisRepository.findAllByOrderByNombreAsc();
    }


    @Override
    public Pais findById(Long paisId) {
        return this.paisRepository.findById(paisId)
                .orElseThrow(() -> new LocalizationException("País no encontrado con id: " + paisId));
    }


    @Override
    public Pais findByNombre(String pais) {
        return this.paisRepository.findByNombre(pais)
                .orElseThrow(() -> new LocalizationException("País no encontrado con nombre: " + pais));
    }


    @Override
    public Pais findByCodigo(String code) {
        return this.paisRepository.findByIso2(code)
                .orElseThrow(() -> new LocalizationException("País no encontrado con código: " + code));
    }
}
