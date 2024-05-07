package br.com.develfood.develfood.Services;

import br.com.develfood.develfood.Class.Email;
import br.com.develfood.develfood.Class.User;
import br.com.develfood.develfood.Enum.StatusEmail;
import br.com.develfood.develfood.Repository.EmailRepository;
import br.com.develfood.develfood.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailService {

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private UserRepository userRepository;

    public void sendRegistrationEmail(String userEmail) {
        Email email = new Email();
        email.setEmailFrom("minhanerf@gmail.com");
        email.setEmailTo(userEmail);
        email.setTitle("Agradecemos seu registro");
        email.setTexto("Olá, \n\nObrigado por se registrar em nosso sistema. Esperamos que tenha uma ótima experiência!\n\nAtenciosamente, \nDevelFood");

        enviarEmail(email);
    }

    public void enviarEmail(Email email) {
        email.setTempo(LocalDateTime.now());
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(email.getEmailFrom());
            message.setTo(email.getEmailTo());
            message.setSubject(email.getTitle());
            message.setText(email.getTexto());

            emailSender.send(message);

            email.setStatus(StatusEmail.SENT);
        } catch (MailException e) {
            email.setStatus(StatusEmail.ERROR);
            e.printStackTrace();
        } finally {
            emailRepository.save(email);
        }
    }

    public void sendPasswordRecoveryEmail(String userEmail, String recoveryToken) {
        try {
            User user = userRepository.findByUserEmail(userEmail);
            if (user == null) {
                System.err.println("Usuário não encontrado com o e-mail fornecido: " + userEmail);
                return;
            }

            recoveryToken = user.getRecoveryToken();

            if (recoveryToken == null || recoveryToken.isEmpty()) {
                System.err.println("Token de recuperação inválido para o usuário: " + userEmail);
                return;
            }

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("seu_email@gmail.com");
            message.setTo(userEmail);
            message.setSubject("Recuperação de Senha");
            message.setText("Olá,\n\nPara redefinir sua senha, utilize o seguinte token de recuperação: " + recoveryToken + "\n\nAtenciosamente,\nDevelFood");

            emailSender.send(message);

        } catch (MailException e) {
            System.err.println("Erro ao enviar e-mail de recuperação de senha: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
