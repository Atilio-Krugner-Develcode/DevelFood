package br.com.develfood.develfood.Repository;

import br.com.develfood.develfood.Class.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao , Long> {
    List<Avaliacao> findByRestauranteId(Long restauranteId);}
