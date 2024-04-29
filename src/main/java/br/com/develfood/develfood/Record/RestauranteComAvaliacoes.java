package br.com.develfood.develfood.Record;


import br.com.develfood.develfood.Class.Avaliacao;
import br.com.develfood.develfood.Class.PlateFilter;

import java.util.List;

public record RestauranteComAvaliacoes(
        Long id,
        String nome,
        String cpf,
        int telefone,
        String foto,
        PlateFilter plateFilter,
        List<Avaliacao> avaliacoes,
        java.math.BigDecimal mediaDasNotas) {}
