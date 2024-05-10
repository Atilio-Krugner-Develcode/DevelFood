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



    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private BigDecimal fullPrice;
    private int quantity;
    private String status;
    private LocalDate date;
    private String paymentType;

    public Pedido(PedidoDTO body) {
        this.fullPrice = body.fullPrice();
        this.quantity = body.quantity();
        this.status = body.status();
        this.date = LocalDate.now();
        this.paymentType = body.paymentType();
    }


    }




