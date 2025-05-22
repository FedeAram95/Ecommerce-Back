package com.ecommerce.ecommerce.ventas.dto;

import java.util.Date;
import java.util.List;

import com.ecommerce.ecommerce.clientes.entities.Cliente;
import com.ecommerce.ecommerce.clientes.entities.Direccion;
import com.ecommerce.ecommerce.operaciones.entities.DetalleOperacion;
import com.ecommerce.ecommerce.operaciones.entities.EstadoOperacion;
import com.ecommerce.ecommerce.pagos.entities.MedioPago;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VentaPayload {
    private Long nroOperacion;
    private Date fechaOperacion;
    private Date fechaEnvio;
    private Date fechaEntrega;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private EstadoOperacion estado;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Cliente cliente;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Direccion direccionEnvio;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private MedioPago medioPago;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<DetalleOperacion> items;
    private Double total;
}
