package br.com.develfood.develfood.Record;

import br.com.develfood.develfood.Class.Plates;

import java.math.BigDecimal;

public record PlatesDTO(

        Long id,
        String nome,
        String descricao,
        String foto,
        BigDecimal preco,
        String categoria
) {

}
