package com.ecommerce.ecommerce.autenticacion.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.ecommerce.autenticacion.entities.Rol;
import com.ecommerce.ecommerce.autenticacion.entities.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Obtiene un usuario por el email.
     * @param email String email del usuario.
     * @return Optional con el usuario.
     */
    Optional<Usuario> findByEmail(String email);

    /**
     * Valida si el usuario ya está registrado en la BD con el email pasado por parámetro.
     * @param email String email a validar.
     * @return boolean: true si existe; false si no.
     */
    Boolean existsByEmail(String email);

    /**
     * Obtiene los usuarios ordenados por fecha de creación de manera descendiente.
     * @return List usuarios.
     */
    List<Usuario> findAllByOrderByFechaCreacionDesc();

    /**
     * Obtiene todos los usuarios de un cierto rol.
     * @param rol Rol de usuarios a obtener.
     * @return List usuarios.
     */
    List<Usuario> findAllByRol(Rol rol);
}
