package br.com.develfood.develfood.Class;

import br.com.develfood.develfood.Class.Pedido.Pedido;
import br.com.develfood.develfood.Record.RequestRestaurant;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "restaurant")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(of = "id")
public class Restaurant {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id

    private Long id;
    private String name;
    private String cnpj;
    private String phone;
    private String image;

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

    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Avaliacao> avaliacoes;

    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<RestaurantePromocao> promocoes;



    public static Restaurant findById(Long id) {
        return findById(id);
    }

    public void updateRestaurant(RequestRestaurant restaurant) {
        if (restaurant.nome() != null) {
            this.name = restaurant.nome();
        }
        if (restaurant.cnpj() != null) {
            this.cnpj = restaurant.cnpj();
        }

    }

    public Restaurant(RequestRestaurant requestRestaurant){
        this.name = requestRestaurant.nome();
        this.cnpj = requestRestaurant.cnpj();
        this.phone = String.valueOf(requestRestaurant.telefone());
        this.image = requestRestaurant.foto();
        this.plateFilter = requestRestaurant.plateFilter();
    }
}
