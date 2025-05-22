package com.ecommerce.ecommerce.autenticacion.bootstrap;

import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.ecommerce.ecommerce.autenticacion.entities.AuthProvider;
import com.ecommerce.ecommerce.autenticacion.entities.Rol;
import com.ecommerce.ecommerce.autenticacion.entities.Usuario;
import com.ecommerce.ecommerce.autenticacion.exceptions.AutenticacionException;
import com.ecommerce.ecommerce.autenticacion.repositories.RolRepository;
import com.ecommerce.ecommerce.autenticacion.repositories.UsuarioRepository;
import com.ecommerce.ecommerce.clientes.entities.Cliente;
import com.ecommerce.ecommerce.perfiles.services.PerfilService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class DataLoaderAutenticacion implements CommandLineRunner {

    private final RolRepository rolRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    private final PerfilService perfilService;

    @Override
    public void run(String... args) throws Exception {

        if (this.rolRepository.findByNombre("ROLE_ADMIN").isEmpty()) {
            Rol admin = Rol.builder()
                    .nombre("ROLE_ADMIN")
                    .build();
            try {
                this.rolRepository.save(admin);
            } catch (DataIntegrityViolationException e) {
                e.getMessage();
            }

        }

        if (this.rolRepository.findByNombre("ROLE_USER").isEmpty()) {
            Rol user = Rol.builder()
                    .nombre("ROLE_USER")
                    .build();

            try {
                this.rolRepository.save(user);
            } catch (DataIntegrityViolationException e) {
                e.getMessage();
            }

        }

        if (this.usuarioRepository.findByEmail("pruebas@wantfrom.es").isEmpty()) {
            Usuario administrador = Usuario.builder()
                    .email("pruebas@wantfrom.es")
                    .enabled(true)
                    .authProvider(AuthProvider.local)
                    .providerId("1")
                    .fechaCreacion(new Date())
                    .rol(this.rolRepository.findByNombre("ROLE_ADMIN").orElse(null))
                    .password(passwordEncoder.encode("1224"))
                    .build();

            Cliente clienteAdmin = Cliente.builder()
                    .apellido("Deofis")
                    .nombre("Wantfrom")
                    .direccion(null)
                    .email("pruebas@wantfrom.es")
                    .dni(0L)
                    .fechaNacimiento(new Date()).build();

            try {
                this.perfilService.cargarPerfil(clienteAdmin, administrador);
            } catch (DataIntegrityViolationException e) {
                throw new AutenticacionException(e.getMessage());
            }

        }

    }
}
