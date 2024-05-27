package br.com.develfood.develfood.Record;

import br.com.develfood.develfood.Class.Pedido.Pedido;
import br.com.develfood.develfood.Class.Plates;

import java.math.BigDecimal;
import java.util.List;

public record PedidosDTO(
        Long id,
        BigDecimal total,
        int quantidade,
        String status,
        List<Plates> platesList,
        String data,
        String paymentType,
        String observacao
) {

}






