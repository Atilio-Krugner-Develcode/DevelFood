package br.com.develfood.develfood.Class;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cartoes")
@EqualsAndHashCode(of = "id")
public class Cartoes {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id

    private Long id;


    @OneToOne
    private Cliente cliente;
}
