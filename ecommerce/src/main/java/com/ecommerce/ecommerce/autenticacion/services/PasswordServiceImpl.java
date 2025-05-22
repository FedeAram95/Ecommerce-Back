package com.ecommerce.ecommerce.autenticacion.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.ecommerce.autenticacion.dto.CambiarPasswordRequest;
import com.ecommerce.ecommerce.autenticacion.entities.Usuario;
import com.ecommerce.ecommerce.autenticacion.entities.VerificationToken;
import com.ecommerce.ecommerce.autenticacion.exceptions.AutenticacionException;
import com.ecommerce.ecommerce.autenticacion.repositories.UsuarioRepository;
import com.ecommerce.ecommerce.emails.dto.SimpleNotificacionEmail;
import com.ecommerce.ecommerce.emails.services.MailService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PasswordServiceImpl implements PasswordService {

    private final AutenticacionService autenticacionService;
    private final UsuarioRepository usuarioRepository;
    private final VerificationTokenService verificationTokenService;
    private final PasswordEncoder passwordEncoder;
    private final String clientUrl;
    private final MailService mailService;

    public PasswordServiceImpl(
        AutenticacionService autenticacionService,
        UsuarioRepository usuarioRepository,
        VerificationTokenService verificationTokenService,
        PasswordEncoder passwordEncoder,
        @Value("${client.url}") String clientUrl,
        @Qualifier("simpleMailService") MailService mailService
    ) {
        this.autenticacionService = autenticacionService;
        this.usuarioRepository = usuarioRepository;
        this.verificationTokenService = verificationTokenService;
        this.passwordEncoder = passwordEncoder;
        this.clientUrl = clientUrl;
        this.mailService = mailService;
    }

    @Transactional
    @Override
    public Usuario cambiarPassword(CambiarPasswordRequest passwordRequest) {
        if (this.autenticacionService.estaLogueado()) {
            log.info("LOGUEADO");

            Usuario usuario = this.autenticacionService.getUsuarioActual();
            usuario.setPassword(passwordEncoder.encode(passwordRequest.getPassword()));
            return this.usuarioRepository.save(usuario);
        } else {
            log.info("NO LOGUEADO");
            throw new AutenticacionException("Usuario no logueado en el sistema");
        }
    }

    @Transactional
    @Override
    public Usuario cambiarPassword(String token, CambiarPasswordRequest cambiarPasswordRequest) {
        VerificationToken verificationToken = this.verificationTokenService.getVerificationToken(token);

        String usuarioEmail = verificationToken.getUsuario().getEmail();
        Usuario usuarioCambiarPass = this.usuarioRepository.findByEmail(usuarioEmail)
                .orElseThrow(() -> new AutenticacionException("No se encontro el usuario: " + usuarioEmail));

        usuarioCambiarPass.setPassword(passwordEncoder.encode(cambiarPasswordRequest.getPassword()));
        this.verificationTokenService.delete(verificationToken);

        return this.usuarioRepository.save(usuarioCambiarPass);
    }

    @Transactional
    @Override
    public void recuperarPassword(String userEmail) {
        Usuario usuario = this.usuarioRepository.findByEmail(userEmail)
                .orElseThrow(() -> new AutenticacionException("No se encontro el usuario: " + userEmail));

        String token = this.verificationTokenService.generarVerificationToken(usuario);
        this.enviarEmail(userEmail, token);
    }

    private void enviarEmail(String userEmail, String token) {
        String title = "Restablece tu contraseña";
        String bodyMessage = "¡No te preocupes!, puedes restablecer tu contraseña haciendo click en el siguiente enlace:";
        String redirectUrl = this.clientUrl.concat("/recuperar-password/").concat(token);
        String subject = "Recuperar contraseña Wantfrom";

        SimpleNotificacionEmail notificacionEmail = SimpleNotificacionEmail.builder()
                .title(title)
                .body(bodyMessage)
                .subject(subject)
                .redirectUrl(redirectUrl)
                .recipient(userEmail)
                .build();

        this.mailService.sendEmail(notificacionEmail);
    }
}
