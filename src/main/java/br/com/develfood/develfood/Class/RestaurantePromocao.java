package br.com.develfood.develfood.Class;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "promocao")
public class RestaurantePromocao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String foto;

    @Column(name = "data_final")
    private LocalDate dataFinal;

    @Column(name = "data_inicial")
    private LocalDate dataInicial;

    @Column(name = "ativa")
    private boolean ativa;

    @ManyToOne
    @JoinColumn(name = "restaurante_id")
    private Restaurant restaurante;
}