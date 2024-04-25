package br.com.develfood.develfood.Class.Pedido;

import br.com.develfood.develfood.Class.Plates;
import br.com.develfood.develfood.Class.Restaurant;
import br.com.develfood.develfood.Record.PlatesDTO;
import br.com.develfood.develfood.Record.RequestRestaurant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDetalhado {
    private Long id;
    private BigDecimal total;
    private int quantidade;
    private String estatus;
    private LocalDate data;
    private String formaPagamento;
    private Plates prato;
    private Restaurant restaurante;


}
