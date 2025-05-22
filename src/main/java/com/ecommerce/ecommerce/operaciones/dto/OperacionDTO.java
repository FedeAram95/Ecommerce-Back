package com.ecommerce.ecommerce.operaciones.dto;

import java.util.Date;

import com.ecommerce.ecommerce.pagos.entities.MedioPagoEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Deprecated
public class OperacionDTO {

    private Long nroOperacion;
    private String estado;
    private Date fechaOperacion;
    private Date fechaEnvio;
    private Date fechaEntrega;
    private String cliente;
    private MedioPagoEnum medioPago;
    private Double total;
}
