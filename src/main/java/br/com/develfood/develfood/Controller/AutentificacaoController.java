package br.com.develfood.develfood.Controller;


import br.com.develfood.develfood.Class.User;
import br.com.develfood.develfood.Record.AuthenticationDTO;
import br.com.develfood.develfood.Record.LoginResponseDTO;
import br.com.develfood.develfood.Record.RegisterDTO;
import br.com.develfood.develfood.Repository.UserRepository;
import br.com.develfood.develfood.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AutentificacaoController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Validated AuthenticationDTO data){
        var usernamepassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamepassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return  ResponseEntity.ok(new LoginResponseDTO(token));
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
