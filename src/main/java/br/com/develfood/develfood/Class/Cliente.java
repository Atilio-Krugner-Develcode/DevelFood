package br.com.develfood.develfood.Class;

import br.com.develfood.develfood.Class.Pedido.Pedido;
import br.com.develfood.develfood.Record.ClientDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "cliente")

public class Cliente {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id

    private Long id;
    private String email;
    private String nome;
    private String sobrenome;
    private String cpf;
    private int telefone;
    private String foto;



    @OneToMany(mappedBy = "cliente")
    @JsonIgnore
    private List<Endereco> endereco;

    @OneToMany(mappedBy = "cliente")
    @JsonIgnore
    private List<Pedido>pedido;


    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<PratosFavoritos> pratosFavoritos;



    public Cliente(ClientDTO data) {
        this.email = data.email();
        this.nome = data.nome();
        this.sobrenome = data.sobrenome();
        this.cpf = data.cpf();
        this.telefone = data.telefone();
        this.foto = data.foto();

    }


}
