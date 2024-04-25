package br.com.develfood.develfood.Class;

import br.com.develfood.develfood.Class.Pedido.Pedido;
import br.com.develfood.develfood.Record.RequestRestaurant;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "restaurant")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Restaurant {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id

    private Long id;
    private String nome;
    private String cpf;
    private int telefone;
    private String foto;

    @ManyToOne
    @JoinColumn(name = "tipos")
    private PlateFilter plateFilter;


    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Plates> pratos;

    @OneToMany(mappedBy = "restaurant")
    @JsonIgnore
    private List<Endereco> endereco;

    @OneToMany(mappedBy = "restaurantes")
    @JsonIgnore
    private List<Pedido>pedido;



    public static Restaurant findById(Long id) {
        return findById(id);
    }

    public void updateRestaurant(RequestRestaurant restaurant) {
        if (restaurant.nome() != null) {
            this.nome = restaurant.nome();
        }
        if (restaurant.cpf() != null) {
            this.cpf = restaurant.cpf();
        }

    }

    public Restaurant(RequestRestaurant requestRestaurant){
        this.nome = requestRestaurant.nome();
        this.cpf = requestRestaurant.cpf();
        this.telefone = requestRestaurant.telefone();
        this.foto = requestRestaurant.foto();
        this.plateFilter = requestRestaurant.plateFilter();
    }
}
