package com.ecommerce.ecommerce.emails.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.autenticacion.exceptions.MailSenderException;
import com.ecommerce.ecommerce.emails.dto.EmailBody;
import com.ecommerce.ecommerce.emails.dto.NotificacionEmail;
import com.ecommerce.ecommerce.operaciones.entities.Operacion;

import lombok.extern.slf4j.Slf4j;

/**
 * Implementación orientada al envío de emails para {@link com.ecommerce.ecommerce.operaciones.entities.Operacion}es.
 * Implementa {@link MailService} para concretar el envío.
 */
@Service
@Qualifier("operacionMailService")
@Slf4j
public class OperacionMailService implements MailService {

    private final JavaMailSender javaMailSender;
    private final MailContentBuilder mailContentBuilder;
    private final String mailFrom;

    public OperacionMailService(JavaMailSender javaMailSender,
                               MailContentBuilder mailContentBuilder,
                               @Value("${mail.from.email}") String mailFrom) {
        this.javaMailSender = javaMailSender;
        this.mailContentBuilder = mailContentBuilder;
        this.mailFrom = mailFrom;
    }

    @Override
    @Async
    public void sendEmail(NotificacionEmail notificacionEmail) {
        EmailBody body = (EmailBody) notificacionEmail.getBody();

        String title = body.getTitle();
        String message = body.getMessage();
        Operacion content = (Operacion) body.getContent();
        String redirectUrl = notificacionEmail.getRedirectUrl();

        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(mailFrom);
            messageHelper.setTo(notificacionEmail.getRecipient());
            messageHelper.setSubject(notificacionEmail.getSubject());
            messageHelper.setText(mailContentBuilder.build(title, message, redirectUrl, content), true);
        };

        try {
            javaMailSender.send(messagePreparator);
            log.info("Email sent!");
        } catch (MailException e) {
            throw new MailSenderException("Excepción al enviar email: " + e.getMessage());
        }
    }
}
