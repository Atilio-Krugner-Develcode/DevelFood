package br.com.develfood.develfood.Controller;


import br.com.develfood.develfood.Class.User;
import br.com.develfood.develfood.Record.*;
import br.com.develfood.develfood.Repository.UserRepository;
import br.com.develfood.develfood.Services.PasswordRecoveryService;
import br.com.develfood.develfood.infra.security.TokenService;
import br.com.develfood.develfood.Services.EmailService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("auth")
public class AutentificacaoController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordRecoveryService passwordRecoveryService;


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Validated AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.userEmail(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/registrar")
    public ResponseEntity registrar(@RequestBody @Validated RegisterDTO data) {
        if (this.repository.findByUserEmail(data.userEmail()) != null) {
            return ResponseEntity.badRequest().build();
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.userEmail(), encryptedPassword, data.userEmail(), data.role());
        this.repository.save(newUser);
        emailService.sendRegistrationEmail(newUser.getUserEmail());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity logout(HttpServletRequest request) {

        request.getSession().invalidate();

        return ResponseEntity.ok().body("Logout realizado com sucesso!");
    }


    @PostMapping("/redefinir-senha")
    public ResponseEntity redefinirSenha(@RequestParam("token") String token, @RequestBody @Valid RedefinirSenhaDTO data) {
        if (passwordRecoveryService.isValidRecoveryToken(token)) {
            User user = passwordRecoveryService.findUserByRecoveryToken(token);

            if (user != null) {
                String encryptedPassword = new BCryptPasswordEncoder().encode(data.getNovaSenha());
                user.setPassword(encryptedPassword);
                repository.save(user);

                passwordRecoveryService.removeToken(token);

                return ResponseEntity.ok("Senha redefinida com sucesso.");
            }
        }

        return ResponseEntity.badRequest().body("Token de recuperação inválido ou expirado.");
    }



    @PostMapping("/esqueci-senha")
    public ResponseEntity esqueciSenha(@RequestBody @Valid EsqueciSenhaDTO data) {
        User user = (User) repository.findByLogin(data.getEmail());

        if (user == null) {
            return ResponseEntity.badRequest().body("Usuário não encontrado.");
        }

        String recoveryToken = UUID.randomUUID().toString();
        passwordRecoveryService.associateTokenWithUser(recoveryToken, user);

        emailService.sendPasswordRecoveryEmail(user.getEmail(), recoveryToken);

        return ResponseEntity.ok("Email de recuperação de senha enviado com sucesso.");
    }
}