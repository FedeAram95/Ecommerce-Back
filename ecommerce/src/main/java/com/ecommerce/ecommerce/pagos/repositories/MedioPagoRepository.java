package com.ecommerce.ecommerce.pagos.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.ecommerce.pagos.entities.MedioPago;
import com.ecommerce.ecommerce.pagos.entities.MedioPagoEnum;

@Repository
public interface MedioPagoRepository extends JpaRepository<MedioPago, Long> {

    Optional<MedioPago> findByNombre(MedioPagoEnum medioPago);
}
