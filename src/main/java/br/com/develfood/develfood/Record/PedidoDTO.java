package br.com.develfood.develfood.Record;

import br.com.develfood.develfood.Class.Pedido.Pedido;
import br.com.develfood.develfood.Class.Plates;

import java.math.BigDecimal;

public record PedidoDTO(

        Long id,
        BigDecimal fullPrice,
        int quantity,
        String status,
        Plates plates,
        String data,
        String paymentType
) {
    public PedidoDTO(Pedido pedido) {
        this(
                pedido.getId(),
                pedido.getFullPrice(),
                pedido.getQuantity(),
                "PEDIDO_REALIZADO",
                pedido.getPlates(),
                pedido.getDate().toString(),
                pedido.getPaymentType()
        );
    }
}



