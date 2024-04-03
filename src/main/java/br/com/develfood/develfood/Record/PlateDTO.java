package br.com.develfood.develfood.Record;

import br.com.develfood.develfood.Class.PlateFilter;

import java.math.BigDecimal;

public record PlateDTO(
        Long id,
        String nome,
        String descricao,
        String foto,
        String categoria,
        BigDecimal preco,
        PlateFilter plateFilter

) {
}
