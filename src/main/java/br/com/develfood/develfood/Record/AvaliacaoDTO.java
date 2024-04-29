package br.com.develfood.develfood.Record;

import java.math.BigDecimal;

public record AvaliacaoDTO(
        Long id,
        String descricao,
        BigDecimal nota
) {
}
