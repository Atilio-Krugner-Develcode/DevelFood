package br.com.develfood.develfood.Class;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "avaliacao")
@EqualsAndHashCode(of = "id")
public class Avaliacao {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id

    private Long id;
    private String descricao;
    private double nota;

    @ManyToOne
    private Cliente cliente;

    @ManyToOne
    private Restaurante restaurante;
}
