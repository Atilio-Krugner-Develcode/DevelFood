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
    private String firstName;
    private String lastName;
    @Column(unique = true)
    @Size(min = 11, max = 11)
    private String cpf;
    @Column(unique = true)
    private String phone;
    private String image;



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
        this.firstName = data.nome();
        this.lastName = data.sobrenome();
        this.cpf = data.cpf();
        this.phone = String.valueOf(data.telefone());
        this.image = data.foto();

    }


}
