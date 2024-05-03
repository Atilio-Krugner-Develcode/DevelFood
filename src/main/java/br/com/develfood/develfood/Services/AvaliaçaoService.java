package br.com.develfood.develfood.Services;

import br.com.develfood.develfood.Class.Avaliacao;
import br.com.develfood.develfood.Class.Restaurant;
import br.com.develfood.develfood.Record.AvaliacaoDTO;
import br.com.develfood.develfood.Record.RestauranteComAvaliacoes;
import br.com.develfood.develfood.Repository.AvaliacaoRepository;
import br.com.develfood.develfood.Repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AvaliaçaoService {
    @Autowired
    private RestaurantRepository restauranteRepository;

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    public RestauranteComAvaliacoes getRestauranteComAvaliacoes(Long id) {
        Restaurant restaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Restaurante não encontrado com o ID: " + id));

        List<Avaliacao> avaliacoes = avaliacaoRepository.findByRestauranteId(id);

        BigDecimal mediaDasNotas = calcularMediaDasNotas(avaliacoes);

        return new RestauranteComAvaliacoes(
                restaurante.getId(),
                restaurante.getNome(),
                restaurante.getCnpj(),
                restaurante.getTelefone(),
                restaurante.getFoto(),
                restaurante.getPlateFilter(),
                avaliacoes,
                mediaDasNotas
        );
    }

    private BigDecimal calcularMediaDasNotas(List<Avaliacao> avaliacoes) {
        if (avaliacoes == null || avaliacoes.isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal somaDasNotas = BigDecimal.ZERO;
        for (Avaliacao avaliacao : avaliacoes) {
            somaDasNotas = somaDasNotas.add(avaliacao.getNota());
        }

        BigDecimal quantidadeDeAvaliacoes = BigDecimal.valueOf(avaliacoes.size());
        return somaDasNotas.divide(quantidadeDeAvaliacoes, 2, RoundingMode.HALF_UP);
    }

    public Avaliacao avaliarRestaurante(Long restauranteId, AvaliacaoDTO avaliacaoDTO) {
        Restaurant restaurante = restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new NoSuchElementException("Restaurante não encontrado com o ID: " + restauranteId));

        BigDecimal nota = avaliacaoDTO.nota();
        validarNota(nota);

        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setRestaurante(restaurante);
        avaliacao.setNota(nota);
        avaliacao.setDescricao(avaliacaoDTO.descricao());

        return avaliacaoRepository.save(avaliacao);
    }

    private void validarNota(BigDecimal nota) {
        if (nota.compareTo(BigDecimal.ZERO) < 0 || nota.compareTo(BigDecimal.valueOf(5)) > 0) {
            throw new IllegalArgumentException("A nota deve estar entre 0 e 5");
        }
    }
    public Avaliacao atualizarAvaliacao(Long avaliacaoId, AvaliacaoDTO avaliacaoDTO) {
        Avaliacao avaliacao = avaliacaoRepository.findById(avaliacaoId)
                .orElseThrow(() -> new NoSuchElementException("Avaliação não encontrada com o ID: " + avaliacaoId));

        BigDecimal nota = avaliacaoDTO.nota();
        validarNota(nota);

        avaliacao.setNota(nota);
        avaliacao.setDescricao(avaliacaoDTO.descricao());

        return avaliacaoRepository.save(avaliacao);
    }

    public void excluirAvaliacao(Long avaliacaoId) {
        Avaliacao avaliacao = avaliacaoRepository.findById(avaliacaoId)
                .orElseThrow(() -> new NoSuchElementException("Avaliação não encontrada com o ID: " + avaliacaoId));

        avaliacaoRepository.delete(avaliacao);
    }

}




