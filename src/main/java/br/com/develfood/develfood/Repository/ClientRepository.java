package br.com.develfood.develfood.Repository;

import br.com.develfood.develfood.Class.Cliente;
import br.com.develfood.develfood.Class.Plates;
import br.com.develfood.develfood.Class.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Cliente, Long> {

    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);
    boolean existsByPhone(String phone);

    Optional<Cliente> findByUser(User user);

    Optional<Cliente> findByUserId(Long userId);

    boolean existsByPratosFavoritos_ClienteAndPratosFavoritos_Prato(@NonNull Cliente cliente, @NonNull Plates prato);

}
