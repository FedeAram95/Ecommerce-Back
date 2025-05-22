package com.ecommerce.ecommerce.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@ConfigurationProperties(prefix = "file.uploads")
@Configuration
@Data
public class UploadsConfig {

    private String UPLOADS_DIR;

    @Bean
    public String uploadsDir() {
        return this.UPLOADS_DIR;
    }

}
