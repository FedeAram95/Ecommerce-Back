package com.ecommerce.ecommerce.catalogo.consultas.services;

import com.ecommerce.ecommerce.catalogo.consultas.dto.ConsultaPayload;

public interface ConsultaService {

    /**
     * Servicio para enviar mensaje de consulta sobre cierto producto.
     * @param consultaPayload {@link ConsultaPayload} con los datos para la consulta.
     */
    void enviarConsulta(ConsultaPayload consultaPayload);

}
