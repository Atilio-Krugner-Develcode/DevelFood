package br.com.develfood.develfood.Services;

import br.com.develfood.develfood.Class.User;
import br.com.develfood.develfood.Repository.UserRepository;
import br.com.develfood.develfood.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AutenticationService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    public String autenticarUsuario(String login, String senha) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(login, senha);
        var auth = this.authenticationManager.authenticate(usernamePassword);
        return tokenService.generateToken((User) auth.getPrincipal());
    }

    public boolean registrarUsuario(String login, String senha, String role) {
        if(userRepository.findByLogin(login) != null) {
            return false; // Usuário já existe
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(senha);
        User newUser = new User(login, encryptedPassword, role);
        userRepository.save(newUser);
        return true;
    }
}
