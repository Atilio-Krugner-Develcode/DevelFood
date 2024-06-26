package br.com.develfood.develfood.Controller;


import br.com.develfood.develfood.Class.User;
import br.com.develfood.develfood.Record.*;
import br.com.develfood.develfood.Repository.UserRepository;
import br.com.develfood.develfood.Services.PasswordRecoveryService;
import br.com.develfood.develfood.Services.UserService;
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
import java.util.Random;

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
    private UserService userService;

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
    public User registrar(@RequestBody @Validated RegisterDTO data) {
        return userService.registerUser(data);
    }

    @PostMapping("/logout")
    public ResponseEntity logout(HttpServletRequest request) {

        request.getSession().invalidate();

        return ResponseEntity.ok().body("Logout realizado com sucesso!");
    }


    @PostMapping("/redefinir-senha")
    public ResponseEntity redefinirSenha(@RequestBody @Valid RedefinirSenhaRequest request) {
        String codigoVerificacao = request.getCodigoVerificacao();
        String novaSenha = request.getNovaSenha();

        User user = passwordRecoveryService.findUserByVerificationCode(codigoVerificacao);

        if (user == null) {
            return ResponseEntity.badRequest().body("Código de verificação inválido.");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(novaSenha);

        user.setPassword(encryptedPassword);
        user.setVerificationCode(null);
        repository.save(user);
        return ResponseEntity.ok("Senha redefinida com sucesso.");
    }


    @PostMapping("/esqueci-senha")
    public ResponseEntity esqueciSenha(@RequestBody @Valid EsqueciSenhaDTO data) {
        User user = repository.findByLogin(data.getEmail());
        if (user == null) {
            return ResponseEntity.badRequest().body("Usuário não encontrado.");
        }
        String verificationCode = generateVerificationCode();

        user.setVerificationCode(verificationCode);
        repository.save(user);
        emailService.sendPasswordRecoveryEmail(user.getEmail(), verificationCode);

        return ResponseEntity.ok("Email de recuperação de senha enviado com sucesso.");
    }

    @PostMapping("/verificar-codigo")
    public ResponseEntity<String> verificarCodigoRecuperacao(@RequestBody @Valid VerificarCodigoDTO verificationData) {
        User user = repository.findByLogin(verificationData.getEmail());

        if (user == null) {
            return ResponseEntity.badRequest().body("Usuário não encontrado.");
        }

        if (user.getVerificationCode() == null || !user.getVerificationCode().equals(verificationData.getCodigo())) {
            return ResponseEntity.badRequest().body("Código inválido.");
        }

        return ResponseEntity.ok("Código verificado com sucesso. Agora você pode redefinir sua senha.");
    }



    private String generateVerificationCode() {
        Random random = new Random();
        int code = 1000 + random.nextInt(9000);
        return String.valueOf(code);
    }
}