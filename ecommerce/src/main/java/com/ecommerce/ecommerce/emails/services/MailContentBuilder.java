package com.ecommerce.ecommerce.emails.services;

import java.text.SimpleDateFormat;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.ecommerce.ecommerce.operaciones.entities.Operacion;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MailContentBuilder {

    private final TemplateEngine templateEngine;

    String build(String title, String message, String url) {
        Context context = new Context();
        context.setVariable("title", title);
        context.setVariable("message", message);
        context.setVariable("url", url);

        return templateEngine.process("mailTemplate", context);
    }

    String build(String title, String message, String url, Operacion content) {
        if (content == null) return "error";
        // Calcular mes a partir de int.
        String month = new SimpleDateFormat("MMMM").format(content.getFechaOperacion());
        month = month.substring(0, 1).toUpperCase() + month.substring(1);

        Context context = new Context();
        context.setVariable("title", title);
        context.setVariable("message", message);
        context.setVariable("url", url);
        context.setVariable("operacion", content);
        context.setVariable("medioPago", content.getMedioPago().getNombre());
        context.setVariable("month", month);

        return templateEngine.process("mailTemplate", context);
    }

}
