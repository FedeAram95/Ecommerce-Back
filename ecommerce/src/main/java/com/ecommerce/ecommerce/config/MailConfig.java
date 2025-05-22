package com.ecommerce.ecommerce.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import lombok.Data;

@ConfigurationProperties(prefix = "mail")
@Component
@Data
public class MailConfig {
	@Value("${mail.from.email}")
    private String MAIL_FROM;

    @Bean
    public String mailFrom() {
        return this.MAIL_FROM;
    }
}
