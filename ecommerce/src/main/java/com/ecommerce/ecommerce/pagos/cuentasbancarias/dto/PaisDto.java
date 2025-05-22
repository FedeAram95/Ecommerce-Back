package com.ecommerce.ecommerce.pagos.cuentasbancarias.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaisDto implements Serializable {
    private Long id;
    private String nombre;
    private String codigo;
}
