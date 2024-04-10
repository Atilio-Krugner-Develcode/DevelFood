package br.com.develfood.develfood.services;

import br.com.develfood.develfood.Class.Email;
import br.com.develfood.develfood.Enum.StatusEmail;
import br.com.develfood.develfood.Repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailService {

    @Autowired
    EmailRepository emailRepository;

    @Autowired
    private JavaMailSender emailSender;

    public Email status(Email email) {
        email.setTempo(LocalDateTime.now());
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(email.getEmailFrom());
            message.setTo(email.getEmailTo());
            message.setSubject(email.getTitle());
            message.setText(email.getTexto());
            emailSender.send(message);

            email.setStatus(StatusEmail.SENT);
        }catch (MailException e){
            email.setStatus(StatusEmail.ERROR);
        }finally {
            return emailRepository.save(email);
        }

    }
}
