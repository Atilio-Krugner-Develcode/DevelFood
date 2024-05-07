package br.com.develfood.develfood.Repository;


import br.com.develfood.develfood.Class.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, String> {
    UserDetails findByLogin(String login);

    User findByUserEmail(String userEmail);
    User findByRecoveryToken(String token);
}
