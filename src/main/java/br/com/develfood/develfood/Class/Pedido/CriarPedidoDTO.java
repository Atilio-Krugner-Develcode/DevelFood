package br.com.develfood.develfood.Class.Pedido;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private LocalDate data;
    private String formaPagamento;
    private int quantidade;

}
