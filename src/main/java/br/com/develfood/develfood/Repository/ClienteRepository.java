package br.com.develfood.develfood.Repository;

import br.com.develfood.develfood.Class.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente,String> {
}
