package br.com.develfood.develfood.Class;

import br.com.develfood.develfood.Record.RequestRestaurant;
import jakarta.persistence.*;
import lombok.*;


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
