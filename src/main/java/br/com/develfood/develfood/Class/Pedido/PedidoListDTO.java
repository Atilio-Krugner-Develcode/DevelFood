package br.com.develfood.develfood.Class.Pedido;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO for {@link Pedido}
 */
@Value
@AllArgsConstructor
@Builder
@Getter

public class PedidoListDTO implements Serializable {
    private RestaurantDto restaurantes;
    private List<ItemPedidoDto> itemPedidos;
    private long id;
    private BigDecimal fullPrice;
    private String status;
    private LocalDate date;
    private String paymentType;
}