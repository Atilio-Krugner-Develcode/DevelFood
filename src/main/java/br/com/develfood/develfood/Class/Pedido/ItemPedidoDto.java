package br.com.develfood.develfood.Class.Pedido;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link ItemPedido}
 */
@Value
@AllArgsConstructor
@Builder
@Getter
public class ItemPedidoDto implements Serializable {
    Long id;
    Integer quantidade;
    String observacao;
    BigDecimal price;
    PlatesDto plates;
}