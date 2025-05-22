package com.ecommerce.ecommerce.emails.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.emails.dto.NotificacionEmail;

@Service
@Primary
public class SimpleMailService implements MailService {

    private final JavaMailSender mailSender;
    private final String fromEmail;

    public SimpleMailService(JavaMailSender mailSender,
                             @Value("${mail.from.email}") String fromEmail,
                             @Value("${mail.some.other.property}") String someOtherProperty) {
        this.mailSender = mailSender;
        this.fromEmail = fromEmail;
    }

    @Override
    public void sendEmail(NotificacionEmail notificacionEmail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo("");
        message.setSubject(notificacionEmail.getSubject());
        message.setText("");
        mailSender.send(message);
    }
}
