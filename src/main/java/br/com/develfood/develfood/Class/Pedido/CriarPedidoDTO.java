package br.com.develfood.develfood.Class.Pedido;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CriarPedidoDTO {


    private Long idCliente;
    private Long idPrato;
    private Long idRestaurantes;
    private LocalDate date;
    private String paymentType;
    private int quantity;

}
