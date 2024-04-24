package br.com.develfood.develfood.Class;

import br.com.develfood.develfood.Record.PedidoDTO;
import br.com.develfood.develfood.Record.PlateDTO;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

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




    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private BigDecimal total;
    private int quantidade;
    private String estatus;


    public Pedido(PedidoDTO body) {
        this.total = body.total();
        this.quantidade = body.quantidade();
        this.estatus = body.estatus();

    }

    }




