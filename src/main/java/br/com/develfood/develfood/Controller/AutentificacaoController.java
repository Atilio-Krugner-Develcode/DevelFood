package br.com.develfood.develfood.Controller;


import br.com.develfood.develfood.Class.User;
import br.com.develfood.develfood.Record.AuthenticationDTO;
import br.com.develfood.develfood.Record.LoginResponseDTO;
import br.com.develfood.develfood.Record.RegisterDTO;
import br.com.develfood.develfood.Repository.UserRepository;
import br.com.develfood.develfood.Services.AutenticationService;
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
    private AutenticationService autenticacaoService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Validated AuthenticationDTO data){
        String token = autenticacaoService.autenticarUsuario(data.login(), data.password());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/registrar")
    public ResponseEntity registrar(@RequestBody @Validated RegisterDTO data){
        if(!autenticacaoService.registrarUsuario(data.login(), data.password(), String.valueOf(data.role()))) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
}
