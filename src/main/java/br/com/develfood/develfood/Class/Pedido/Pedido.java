package br.com.develfood.develfood.Class.Pedido;

import br.com.develfood.develfood.Class.Cliente;
import br.com.develfood.develfood.Class.Plates;
import br.com.develfood.develfood.Class.Restaurant;
import br.com.develfood.develfood.Record.PedidoDTO;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Pedido {
    @ManyToOne
    @JoinColumn(name = "pedidoscl")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "pratos")
    private Plates plates;

    @ManyToOne
    @JoinColumn(name = "pedidore")
    private Restaurant restaurantes;

//    @Enumerated(EnumType.STRING)
//    private Estatus status;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private BigDecimal total;
    private int quantidade;
    private String status;
    private LocalDate data;
    private String formaPagamento;

    public Pedido(PedidoDTO body) {
        this.total = body.total();
        this.quantidade = body.quantidade();
        this.status = body.status();
        this.data = LocalDate.now();
        this.formaPagamento = body.formaPagamento();
    }


    }




