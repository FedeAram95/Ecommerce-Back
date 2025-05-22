package com.ecommerce.ecommerce.administracionusuarios.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.ecommerce.administracionusuarios.entities.CambioRol;

@Repository
public interface CambioRolRepository extends JpaRepository<CambioRol, Long> {

    /**
     * Obtiene de la base de datos todos los registros de Cambio de rol ordenados por fecha,
     * de manera ascendente.
     * @return List con los registros.
     */
    List<CambioRol> findAllByOrderByFechaCambioAsc();
}
