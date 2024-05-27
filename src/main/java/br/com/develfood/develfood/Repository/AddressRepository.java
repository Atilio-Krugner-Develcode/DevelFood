package br.com.develfood.develfood.Repository;

import br.com.develfood.develfood.Class.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Endereco, Long> {
}
