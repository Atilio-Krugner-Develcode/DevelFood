package br.com.develfood.develfood.Class.Pedido;

import br.com.develfood.develfood.Class.Cliente;
import br.com.develfood.develfood.Class.Plates;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
public class ItemPedido {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private Integer quantidade;
    private String observacao;
    private BigDecimal price;


    @ManyToOne
    @JoinColumn(name = "pedido_id")
    @JsonIgnore
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "plate_id")
    private Plates plates;


}
