package br.com.develfood.develfood.Class;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Avaliacao {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id

    private Long id;
    private String descricao;
    private BigDecimal nota;

    @ManyToOne
    @JsonIgnore
    private Restaurant restaurante;
}
