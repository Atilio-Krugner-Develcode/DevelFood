package br.com.develfood.develfood.Class;

import br.com.develfood.develfood.Class.Pedido.Pedido;
import br.com.develfood.develfood.Record.ClientDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.Size;
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
    @Column(unique = true)
    private String email;
    private String nome;
    private String sobrenome;
    @Column(unique = true)
    @Size(min = 11, max = 11)
    private String cpf;
    @Column(unique = true)
    private String telefone;
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
        this.telefone = String.valueOf(data.telefone());
        this.foto = data.foto();

    }


}
