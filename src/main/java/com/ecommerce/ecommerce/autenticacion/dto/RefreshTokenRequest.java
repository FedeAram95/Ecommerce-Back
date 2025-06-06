package com.ecommerce.ecommerce.autenticacion.dto;



import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenRequest {

    @NotNull
    private String refreshToken;
    @NotNull
    private String userEmail;
}
