package com.ecommerce.ecommerce.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;

import lombok.Data;

@ConfigurationProperties(prefix = "paypal")
@Configuration
@Data
public class PayPalConfig {

    @Value("${paypal.access_token}")
    private String accessToken;

    @Value("${paypal.public_key}")
    private String publicKey;

    @Bean
    public PayPalEnvironment payPalEnvironment() throws Exception {
        if (this.publicKey == null || this.accessToken == null)
            throw new IllegalAccessException("PayPal client id and client secret must be provided");
        return new PayPalEnvironment.Sandbox(publicKey, accessToken);
    }

    @Bean
    public PayPalHttpClient payPalHttpClient() throws Exception {
        return new PayPalHttpClient(payPalEnvironment());
    }
}
