package br.com.develfood.develfood.Class;

import br.com.develfood.develfood.Record.RequestRestaurant;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "restaurant")
@Entity(name = "restaurant")
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


    @OneToMany(mappedBy = "restaurante")
    private List<Endereco> restaurantePromocao;

    @OneToMany
    private List<Pratos> pratos;



    @OneToMany
    private  List<Avaliacao> avaliacao;

    @OneToMany
    private List<pedido>pedidos;

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
    }
}