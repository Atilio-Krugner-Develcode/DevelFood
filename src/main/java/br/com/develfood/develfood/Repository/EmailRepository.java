package br.com.develfood.develfood.Repository;

import br.com.develfood.develfood.Class.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<Email, Long> {

}
