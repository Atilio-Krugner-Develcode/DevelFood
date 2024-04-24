package br.com.develfood.develfood.Record;

import java.math.BigDecimal;

public record PedidoDTO(

        Long id,
        BigDecimal total,
        int quantidade,
        String estatus
) {
}
