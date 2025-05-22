package com.ecommerce.ecommerce.administracionusuarios.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.ecommerce.administracionusuarios.entities.CambioHabilitacionUsuario;

@Repository
public interface CambioHabilitacionRepository extends JpaRepository<CambioHabilitacionUsuario, Long> {

    /**
     * Obtiene de la base de datos todos los registros de habilitaciones de usuarios ordenados por fecha,
     * de manera ascendente.
     * @return List con los registros.
     */
    List<CambioHabilitacionUsuario> findAllByOrderByFechaCambioAsc();

}
