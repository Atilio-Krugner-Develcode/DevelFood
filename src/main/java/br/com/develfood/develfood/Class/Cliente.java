package br.com.develfood.develfood.Class;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Cliente {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id

    private Long id;
    private String nome;
    private String sobrenome;
    private int cpf;
    private int telefone;
    private int enull;
    private String pratosFavoritos;



    @ManyToMany
    private List<Plates>pratos;

    @OneToMany
    private List<PratosFavoritos>pratosFavoritosList;

    @OneToOne
    private Cartoes cartoes;

    @OneToMany
    private List<Avaliacao> avaliacao;


    @OneToMany
    private List<Pedido>pedidos;

}
