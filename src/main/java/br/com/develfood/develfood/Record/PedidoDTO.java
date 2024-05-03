package br.com.develfood.develfood.Record;

import br.com.develfood.develfood.Class.Pedido.Pedido;
import br.com.develfood.develfood.Class.Plates;

import java.math.BigDecimal;

public record PedidoDTO(

        Long id,
        BigDecimal total,
        int quantidade,
        String status,
        Plates plates,
        String data,
        String formaPagamento
) {
    public PedidoDTO(Pedido pedido) {
        this(
                pedido.getId(),
                pedido.getTotal(),
                pedido.getQuantidade(),
                "PEDIDO_REALIZADO",
                pedido.getPlates(),
                pedido.getData().toString(),
                pedido.getFormaPagamento()
        );
    }
}



