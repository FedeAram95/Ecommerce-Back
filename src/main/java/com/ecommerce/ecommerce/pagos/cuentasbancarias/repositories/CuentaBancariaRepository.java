package com.ecommerce.ecommerce.pagos.cuentasbancarias.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.ecommerce.localizaciones.entities.Pais;
import com.ecommerce.ecommerce.pagos.cuentasbancarias.entities.CuentaBancaria;

@Repository
public interface CuentaBancariaRepository extends JpaRepository<CuentaBancaria, Long> {
    Optional<CuentaBancaria> findByPais(Pais pais);
    Optional<CuentaBancaria> findFirstByPrincipal(boolean principal);
    boolean existsByPais(Pais pais);
}
