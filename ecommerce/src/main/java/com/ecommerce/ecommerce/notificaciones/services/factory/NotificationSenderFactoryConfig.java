package com.ecommerce.ecommerce.notificaciones.services.factory;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class NotificationSenderFactoryConfig {

    private final BeanFactory beanFactory;
    
    public ServiceLocatorFactoryBean notificationSenderFactoryLocator() {
        final ServiceLocatorFactoryBean locator = new ServiceLocatorFactoryBean();
        locator.setServiceLocatorInterface(NotificationSenderFactory.class);
        locator.setBeanFactory(beanFactory);

        return locator;
    }

    @Bean
    public NotificationSenderFactory notificationSenderFactory() {
        final ServiceLocatorFactoryBean locator = notificationSenderFactoryLocator();
        locator.afterPropertiesSet();
        return (NotificationSenderFactory) locator.getObject();
    }
    
}
