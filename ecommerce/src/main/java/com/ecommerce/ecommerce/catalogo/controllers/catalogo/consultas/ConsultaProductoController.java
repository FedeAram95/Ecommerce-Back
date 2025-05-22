package com.ecommerce.ecommerce.catalogo.controllers.catalogo.consultas;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerce.autenticacion.exceptions.MailSenderException;
import com.ecommerce.ecommerce.catalogo.consultas.dto.ConsultaPayload;
import com.ecommerce.ecommerce.catalogo.consultas.services.ConsultaService;
import com.ecommerce.ecommerce.catalogo.productos.exceptions.ProductoException;

import lombok.AllArgsConstructor;

/**
 * API para recibir consultas sobre productos, por parte de los usuarios, que serán
 * enviadas via MAIL a todos los administradores.
 */
@RestController
@RequestMapping("/api/catalogo")
@AllArgsConstructor
public class ConsultaProductoController {

    private final ConsultaService consultaService;

    /**
     * API para enviar consulta sobre un producto en particular.
     * URL: ~/api/catalogo/productos/consulta
     * HttpMethod: POST
     * HttpStatus: OK
     * @param consultaPayload {@link ConsultaPayload} con los datos de la consulta.
     * @return ResponseEntity con mensaje de éxito.
     */
    @PostMapping("/productos/consulta")
    public ResponseEntity<?> consultar(@Valid @RequestBody ConsultaPayload consultaPayload) {
        Map<String, Object> response = new HashMap<>();

        try {
            this.consultaService.enviarConsulta(consultaPayload);
        } catch (ProductoException | MailSenderException e) {
            response.put("mensaje", "Error al enviar la consulta");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "Consulta enviada con éxito");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
