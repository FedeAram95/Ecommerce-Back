package com.ecommerce.ecommerce.ecommerce.services;

import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.ecommerce.entities.EcommerceConfig;
import com.ecommerce.ecommerce.ecommerce.repositories.EcommerceRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EcommerceConfigServiceImpl implements EcommerceConfigService {

    private final EcommerceRepository ecommerceRepository;

    @Override
    public void cambiarColorNav(String color) {
        EcommerceConfig config = this.ecommerceRepository.getOne(1L);
        config.setColorNav(color);
    }

    @Override
    public void cambiarColorFondo(String color) {
        EcommerceConfig config = this.ecommerceRepository.getOne(1L);
        config.setColorFondo(color);
    }

    @Override
    public void cambiarLogo() {
        EcommerceConfig config = this.ecommerceRepository.getOne(1L);

    }
}
