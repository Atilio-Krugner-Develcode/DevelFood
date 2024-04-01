package br.com.develfood.develfood.Class;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Pratos {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id

    private Long id;
    private String nome;
    private String descricao;
    private String foto;
    private double preco;
    private String tipo;

    @ManyToOne
    private Pratos pratos;

    @ManyToOne
    private PlateFilter tiposDeComida;



}
