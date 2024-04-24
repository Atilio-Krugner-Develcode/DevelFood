package br.com.develfood.develfood.Class;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Pedido {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id

    private Long id;
    private double total;
    private boolean pagamentoServico;
    private String formaPagamento;

    @ManyToOne
    private Cliente cliente;

    @ManyToOne
    private Restaurant restaurante;


}
