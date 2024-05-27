package br.com.develfood.develfood.Repository;

import br.com.develfood.develfood.Class.Cliente;
import br.com.develfood.develfood.Class.Plates;
import br.com.develfood.develfood.Class.PratosFavoritos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PratosFavoritosRepository extends JpaRepository<PratosFavoritos, Long> {
    List<PratosFavoritos> findByClienteId(Long clienteId);

    boolean existsByClienteIdAndPratoId(Long clienteId, Long pratoId);


    Optional<PratosFavoritos> findByClienteIdAndPratoId(Long clienteId, Long pratoId);

    Optional<PratosFavoritos> findByClienteAndPrato(Cliente cliente, Plates prato);


}
