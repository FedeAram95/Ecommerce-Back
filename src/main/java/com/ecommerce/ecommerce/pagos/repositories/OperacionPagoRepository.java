package com.ecommerce.ecommerce.pagos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.ecommerce.pagos.entities.OperacionPago;

@Repository
public interface OperacionPagoRepository extends JpaRepository<OperacionPago, String> {

}
