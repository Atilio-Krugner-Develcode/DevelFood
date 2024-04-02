package br.com.develfood.develfood.Class;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pratos_favoritos")
@EqualsAndHashCode(of = "id")
public class PratosFavoritos {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @OneToMany
    @JoinColumn(name = "Pratos")
    private List<PratosFavoritos> pratosFavoritosList;
}
