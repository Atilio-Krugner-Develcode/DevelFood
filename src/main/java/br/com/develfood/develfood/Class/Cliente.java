package br.com.develfood.develfood.Class;

import br.com.develfood.develfood.Record.ClientDTO;
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
    private String nome;
    private String sobrenome;
    private String cpf;
    private int telefone;
    private String foto;





    public Cliente(ClientDTO data) {
        this.nome = data.nome();
        this.sobrenome = data.sobrenome();
        this.cpf = data.cpf();
        this.telefone = data.telefone();
        this.foto = data.foto();
    }
}
