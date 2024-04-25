package br.com.develfood.develfood.Class;

import br.com.develfood.develfood.Class.Pedido.Pedido;
import br.com.develfood.develfood.Record.PlateDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pratos")
@EqualsAndHashCode(of = "id")
public class Plates {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id

    private Long id;
    private String nome;
    private String descricao;
    private String foto;
    private BigDecimal preco;
    private String categoria;


    @ManyToOne
    @JoinColumn(name = "restauranteid")
    private Restaurant restaurante;

    @ManyToOne
    @JoinColumn(name = "plate_filter_id")
    private PlateFilter plateFilter;

    @OneToMany(mappedBy = "plates" )
    @JsonIgnore
    private List<Pedido> pedido;



    public Plates(PlateDTO body) {
        this.nome = body.nome();
        this.descricao = body.descricao();
        this.foto = body.foto();
        this.preco = body.preco();
        this.categoria = body.categoria();
        this.plateFilter = body.plateFilter();
    }

    public void size() {

    }
}
