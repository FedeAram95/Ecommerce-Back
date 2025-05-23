package com.ecommerce.ecommerce.autenticacion.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerce.autenticacion.dto.SignupRequest;
import com.ecommerce.ecommerce.autenticacion.exceptions.AutenticacionException;
import com.ecommerce.ecommerce.autenticacion.exceptions.MailSenderException;
import com.ecommerce.ecommerce.autenticacion.exceptions.PasswordException;
import com.ecommerce.ecommerce.autenticacion.services.AutenticacionService;
import com.ecommerce.ecommerce.clientes.exceptions.ClienteException;
import com.ecommerce.ecommerce.perfiles.services.PerfilService;

import lombok.AllArgsConstructor;

/**
 * This API works for register a new user into the App.
 */

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class RegistrarUsuarioController {

    private final AutenticacionService autenticacionService;
    private final PerfilService perfilService;

    private final String clientUrl;

    /**
     * Recibe una request para registrar una nueva cuenta de usuario. Crea el usuario correspondiente, crea un nuevo
     * perfil, y asocia los datos del cliente con el mismo.
     * URL: ~/api/auth/signup
     * HttpMethod: POST
     * HttpStatus: CREATED
     * @param signupRequest con las credenciales de usuario, y los datos de cliente (Nombre y Apellido).
     * @return ResponseEntity con mensaje de éxito/error y el Perfil nuevo creado.
     */
    @PostMapping("/signup")
    public ResponseEntity<?> signup( @RequestBody SignupRequest signupRequest, BindingResult result) {
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> err.getField() + ": " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("error", "Bad Request");
            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            this.autenticacionService.registrarse(signupRequest);
            this.perfilService.cargarPerfil(signupRequest.getCliente(), signupRequest.getEmail());
        } catch (AutenticacionException | PasswordException | ClienteException | MailSenderException e) {
            response.put("mensaje", "Error al registrar al usuario");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "Usuario registrado exitosamente. " +
                "Comprueba tu correo para activar tu cuenta.");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Verifica una cuenta y marca al usuario como enabled (Habilitado para usar la cuenta).
     * URL: ~/api/auth/accountVerification/aaaa-bbbb-1111-2222
     * HttpMethod: GET
     * HttpStatus: OK
     * @param token String token de activación.
     */
    @GetMapping("/accountVerification/{token}")
    public ResponseEntity<String> verifyToken(@PathVariable String token, HttpServletResponse response) {

        try {
            this.autenticacionService.verificarCuenta(token);
            response.sendRedirect(clientUrl.concat("/verify/redirect"));
        } catch (AutenticacionException | IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
