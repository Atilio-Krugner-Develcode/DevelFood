package br.com.develfood.develfood.Controller;

import br.com.develfood.develfood.Class.Email;
import br.com.develfood.develfood.Record.EmailDTO;
import br.com.develfood.develfood.services.EmailService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    @Autowired
    EmailService emailService;

    @PostMapping("/seding-email")
    public ResponseEntity<Email>sendingEmail(@RequestBody @Validated EmailDTO emailDTO){
        Email email = new Email();
        BeanUtils.copyProperties(emailDTO, email);
        emailService.status(email);
        return new ResponseEntity<>(email, HttpStatus.CREATED);
    }
}
