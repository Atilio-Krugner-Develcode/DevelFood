package br.com.develfood.develfood.Controller.Service;


import br.com.develfood.develfood.Service.User;
import br.com.develfood.develfood.Record.AuthenticationDTO;
import br.com.develfood.develfood.Record.LoginResponseDTO;
import br.com.develfood.develfood.Record.RegisterDTO;
import br.com.develfood.develfood.Repository.UserRepository;
import br.com.develfood.develfood.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.AuthenticationException;

@RestController
@RequestMapping("auth")
public class AutentificacaoController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserRepository repository;


   @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Validated AuthenticationDTO data) {
        try {
            var usernamepassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
            var auth = this.authenticationManager.authenticate(usernamepassword);
            var token = tokenService.generateToken((User) auth.getPrincipal());

            return ResponseEntity.ok(new LoginResponseDTO(token));
        } catch (AuthenticationException e) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falha na autenticação: " + e.getMessage());

        }
    }

        @PostMapping("/registrar")
        public ResponseEntity registrar(@RequestBody @Validated RegisterDTO data){
            if(this.repository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().build();

            String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
            User newUser = new User(data.login(), encryptedPassword, data.role());
            this.repository.save(newUser);
            return ResponseEntity.ok().build();
        }
    }

