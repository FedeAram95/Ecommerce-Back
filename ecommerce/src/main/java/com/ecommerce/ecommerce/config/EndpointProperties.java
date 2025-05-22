package com.ecommerce.ecommerce.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@ConfigurationProperties(prefix = "endpoints")
@Configuration
@Data
public class EndpointProperties {

    private String baseUrl;
    private String clientUrl;

    @Bean
    public String baseUrl() {
        return this.baseUrl;
    }

    @Bean
    public String clientUrl() {
        return this.clientUrl;
    }

}
