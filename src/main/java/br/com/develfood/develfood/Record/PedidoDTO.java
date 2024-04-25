package br.com.develfood.develfood.Record;

import br.com.develfood.develfood.Class.Cliente;
import br.com.develfood.develfood.Class.Pedido;
import br.com.develfood.develfood.Class.Plates;

import java.math.BigDecimal;

public record PedidoDTO(

        Long id,
        BigDecimal total,
        int quantidade,
        String estatus,
        Plates plates,
        Cliente cliente
) {
    public PedidoDTO(Pedido pedido) {
        this(
                pedido.getId(),
                pedido.getTotal(),
                pedido.getQuantidade(),
                "Em preparação",
                pedido.getPlates(),
                pedido.getCliente()
        );
    }


}