package br.com.develfood.develfood.Controller;

import br.com.develfood.develfood.Class.Email;
import br.com.develfood.develfood.Record.EmailDTO;
import br.com.develfood.develfood.Services.EmailService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    @Autowired
    EmailService emailService;

    @PostMapping("/sending-email")
    public ResponseEntity<String> sendingEmail(@RequestBody @Validated EmailDTO emailDTO) {
        Email email = new Email();
        BeanUtils.copyProperties(emailDTO, email);

        try {
            emailService.enviarEmail(email);
            return ResponseEntity.ok("E-mail enviado com sucesso para " + email.getEmailTo());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao enviar e-mail: " + e.getMessage());
        }
    }
}
