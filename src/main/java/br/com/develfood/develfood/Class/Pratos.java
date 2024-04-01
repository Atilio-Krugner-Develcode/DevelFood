package br.com.develfood.develfood.Class;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pratos")
@EqualsAndHashCode(of = "id")
public class Pratos {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id

    private Long id;
    private String nome;
    private String descricao;
    private String foto;
    private BigDecimal preco;
    private String tipo;

    @ManyToOne
    private Pratos pratos;

    @ManyToOne
    private TiposDeComida tiposDeComida;



}
