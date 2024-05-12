package br.com.develfood.develfood.Repository;


import br.com.develfood.develfood.Class.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByLogin(String login);

    User findByUserEmail(String userEmail);
    User findByRecoveryToken(String token);

    User findByVerificationCode(String verificationCode);
}
