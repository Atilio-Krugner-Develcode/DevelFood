package br.com.develfood.develfood.Class;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Endereco {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id

    private Long id;
    private String rua;
    private int numero;
    private String cidade;
    private String bairro;

//    @ManyToOne
//    private Cliente cliente;
//    @ManyToOne
//    private Restaurant restaurante;
}
