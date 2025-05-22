package com.ecommerce.ecommerce.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.mercadopago.MercadoPago;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "mercadopago")
@Data
public class MercadoPagoConfig {

    @Value("${mercadopago.access_token}")
    private String accessToken;

    @Value("${mercadopago.public_key}")
    private String publicKey;
    
    @PostConstruct
    public void init() throws Exception {
        if (this.publicKey == null || this.accessToken == null) {
            throw new IllegalAccessException("MercadoPago access and public key must be provided");
        }
        MercadoPago.SDK.setAccessToken(this.accessToken);
    }
}
