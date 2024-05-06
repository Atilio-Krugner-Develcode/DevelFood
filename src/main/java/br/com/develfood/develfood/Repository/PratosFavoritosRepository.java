package br.com.develfood.develfood.Repository;

import br.com.develfood.develfood.Class.PratosFavoritos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PratosFavoritosRepository extends JpaRepository<PratosFavoritos, Long> {
    List<PratosFavoritos> findByClienteId(int clienteId);
}
