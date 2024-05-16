package br.com.develfood.develfood.Services;

import br.com.develfood.develfood.Class.User;
import br.com.develfood.develfood.Record.RegisterDTO;
import br.com.develfood.develfood.Repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final EmailService emailService;

    public UserService(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    public User registerUser(RegisterDTO data) {
        Optional<User> existingUser = Optional.ofNullable(userRepository.findByUserEmail(data.userEmail()));

        if (existingUser.isPresent()) {
            throw new DataIntegrityViolationException("Já existe um usuário com este email!");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());

        User newUser = new User(data.userEmail(), encryptedPassword, data.userEmail(), data.role());

        userRepository.save(newUser);

//        emailService.sendRegistrationEmail(newUser.getUserEmail());
        new Thread(() -> emailService.sendRegistrationEmail(newUser.getUserEmail())).start();

        return newUser;
    }
}
