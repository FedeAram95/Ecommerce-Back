package com.ecommerce.ecommerce.autenticacion.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {

    private String authToken;
    private String userEmail;
    private String refreshToken;
    private String rol;
    private Date expiraEn;
}
