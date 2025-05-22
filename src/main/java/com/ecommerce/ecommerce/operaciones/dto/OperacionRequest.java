package com.ecommerce.ecommerce.operaciones.dto;

import com.ecommerce.ecommerce.clientes.entities.Direccion;
import com.ecommerce.ecommerce.operaciones.entities.DetalleOperacion;
import com.ecommerce.ecommerce.pagos.entities.MedioPago;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OperacionRequest {
    private Long nroOperacion;
    private DetalleOperacion item;
    private Direccion direccionEnvio;
    private MedioPago medioPago;
}
