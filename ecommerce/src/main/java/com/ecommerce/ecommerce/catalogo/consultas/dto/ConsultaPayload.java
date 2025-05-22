package com.ecommerce.ecommerce.catalogo.consultas.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConsultaPayload {

    private String email;
    private String telefono;
    private String mensaje;
    private Long productoId;

    public ConsultaPayload() {

    }

    public ConsultaPayload(String email, String telefono, String mensaje, Long productoId) {
        this.email = email;
        this.telefono = telefono;
        this.mensaje = mensaje;
        this.productoId = productoId;
    }
}
