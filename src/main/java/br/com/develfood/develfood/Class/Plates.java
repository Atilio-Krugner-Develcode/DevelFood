package br.com.develfood.develfood.Class;

import br.com.develfood.develfood.Class.Pedido.Pedido;
import br.com.develfood.develfood.Record.PlateDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(of = "id")
public class Plates {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id

    private Long id;
    private String name;
    private String description;
    private String image;
    private BigDecimal price;
    private String category;


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
        this.name = body.nome();
        this.description = body.descricao();
        this.image = body.foto();
        this.price = body.preco();
        this.category = body.categoria();
        this.plateFilter = body.plateFilter();
    }

    public void size() {

    }
}
