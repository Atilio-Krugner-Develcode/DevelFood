package br.com.develfood.develfood.Class;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "restaurantePromocao")
@EqualsAndHashCode(of = "id")
public class RestaurantePromocao {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String nome;


    @ManyToOne
    private RestaurantePromocao restaurantePromocao;






}
