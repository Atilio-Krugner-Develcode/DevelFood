package br.com.develfood.develfood.Class;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tiposDeComida")
@EqualsAndHashCode(of = "id")
public class TiposDeComida {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id

    private Long id;
    private String nome;

    @OneToMany
    private List<TiposDeComida> tiposDeComidas;


}
