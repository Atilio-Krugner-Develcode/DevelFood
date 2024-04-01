package br.com.develfood.develfood.Class;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "restaurante")
@EqualsAndHashCode(of = "id")
public class Restaurante {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id

    private Long id;
    private String nome;
    private String cpf;
    private String telefone;

    @OneToOne
    private Endereco endereco;

    @OneToMany(mappedBy = "restaurante")
    private List<Endereco> restaurantePromocao;

    @OneToMany
    private List<Pratos> pratos;

    @ManyToOne
    private TiposDeComida tiposDeComida;

    @OneToMany
    private  List<Avaliacao> avaliacao;

    @OneToMany
    private List<Pedido>pedidos;
}
