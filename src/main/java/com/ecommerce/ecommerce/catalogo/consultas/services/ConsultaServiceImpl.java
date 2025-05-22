package com.ecommerce.ecommerce.catalogo.consultas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.catalogo.consultas.dto.ConsultaPayload;
import com.ecommerce.ecommerce.catalogo.productos.services.ProductoService;
import com.ecommerce.ecommerce.emails.dto.SimpleNotificacionEmail;
import com.ecommerce.ecommerce.emails.services.MailService;

@Service
public class ConsultaServiceImpl implements ConsultaService {

    private final MailService mailService;
    private final ProductoService productoService;

    @Autowired
    public ConsultaServiceImpl( MailService mailService,
                               ProductoService productoService) {
        this.mailService = mailService;
        this.productoService = productoService;
    }

    @Override
    public void enviarConsulta(ConsultaPayload consultaPayload) {
        Long productId = consultaPayload.getProductoId();
        String productName = this.productoService.obtenerProducto(productId).getNombre();

        String subject = "Consulta por producto";
        String bodyTitle = "Nueva consulta para producto " + productName + " con id: " + productId;
        String bodyMessage = consultaPayload.getMensaje().concat(".\n Email:  ".concat(consultaPayload.getEmail()));
        if (consultaPayload.getTelefono() != null) bodyMessage = bodyMessage.concat("\n Teléfono: "
                .concat(consultaPayload.getTelefono()));

        this.enviarEmails(subject, bodyTitle, bodyMessage);
    }

    /**
     * Envía email a la cuenta de consultas.
     * @param subject String subject.
     * @param title String titulo email.
     * @param body String cuerpo con el mensaje del email.
     */
    private void enviarEmails(String subject, String title, String body) {
        SimpleNotificacionEmail notificacionEmail = SimpleNotificacionEmail.builder()
                .recipient("consultas@deofis.com")
                .subject(subject)
                .title(title)
                .body(body).build();

        this.mailService.sendEmail(notificacionEmail);
    }
}
