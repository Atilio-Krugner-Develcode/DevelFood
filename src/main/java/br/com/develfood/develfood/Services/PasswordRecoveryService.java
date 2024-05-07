package br.com.develfood.develfood.Services;
import br.com.develfood.develfood.Class.User;
import br.com.develfood.develfood.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PasswordRecoveryService {

    @Autowired
    private UserRepository userRepository;

    private static final long TOKEN_EXPIRATION_TIME_MS = 15 * 60 * 1000;



    public User findUserByRecoveryToken(String token) {
        return userRepository.findByRecoveryToken(token);
    }

    public boolean isValidRecoveryToken(String token) {
        if (token == null) {
            return false;
        }

        User user = userRepository.findByRecoveryToken(token);
        if (user == null || user.getRecoveryTokenTimestamp() == null) {
            return false;
        }

        long tokenTimestamp = user.getRecoveryTokenTimestamp();
        long currentTime = System.currentTimeMillis();

        return (currentTime - tokenTimestamp) <= TOKEN_EXPIRATION_TIME_MS;
    }

    public void associateTokenWithUser(String token, User user) {
        user.setRecoveryToken(token);
        user.setRecoveryTokenTimestamp(System.currentTimeMillis());
        userRepository.save(user);
    }

    public void removeToken(String token) {
        User user = userRepository.findByRecoveryToken(token);
        if (user != null) {
            user.setRecoveryToken(null);
            user.setRecoveryTokenTimestamp(null);
            userRepository.save(user);
        }
    }

    public void removeExpiredTokens() {
        List<User> users = userRepository.findAll();
        long currentTimestamp = System.currentTimeMillis();

        for (User user : users) {
            Long tokenTimestamp = user.getRecoveryTokenTimestamp();
            if (tokenTimestamp != null && (currentTimestamp - tokenTimestamp) > TOKEN_EXPIRATION_TIME_MS) {
                user.setRecoveryToken(null);
                user.setRecoveryTokenTimestamp(null);
                userRepository.save(user);
            }
        }
    }
}
