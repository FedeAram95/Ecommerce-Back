package com.ecommerce.ecommerce.pagos.cuentasbancarias.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.ecommerce.ecommerce.localizaciones.entities.Pais;

import lombok.Data;

@Entity
@Table(name = "cuentas_bancarias")
@Data
public class CuentaBancaria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nroCuenta;
    @Enumerated(EnumType.STRING)
    private TipoCuentaBancaria tipo;
    @Enumerated(EnumType.STRING)
    private CajaAhorroEnum ca;
    private String alias;
    private String titular;
    private String banco;
    private String email;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pais_id")
    private Pais pais;
    private boolean principal;

    public CuentaBancaria() {

    }

    public CuentaBancaria(String nroCuenta, TipoCuentaBancaria tipo, CajaAhorroEnum ca,
                          String alias, String email, Pais pais, boolean principal,
                          String titular, String banco) {
        this.nroCuenta = nroCuenta;
        this.tipo = tipo;
        this.ca = ca;
        this.alias = alias;
        this.email = email;
        this.pais = pais;
        this.principal = principal;
        this.titular = titular;
        this.banco = banco;
    }

    public static CuentaBancaria crear(String nroCuenta, TipoCuentaBancaria tipo, CajaAhorroEnum ca,
                                       String alias, String email, Pais pais,
                                       String titular, String banco) {
        return new CuentaBancaria(nroCuenta, tipo, ca, alias, email, pais, false, titular, banco);
    }

    public void marcarPrincipal() {
        this.principal = true;
    }

    public void desmarcarPrincipal() {
        this.principal = false;
    }

    boolean validarPais(String pais) {
        return this.pais.getNombre().equalsIgnoreCase(pais);
    }

}
