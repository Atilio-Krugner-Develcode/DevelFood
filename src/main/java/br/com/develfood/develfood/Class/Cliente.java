package br.com.develfood.develfood.Class;

import br.com.develfood.develfood.Dto.RequestCliente;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Cliente {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id

    private Long id;
    private String nome;
    private String sobrenome;
    private int cpf;
    private int telefone;

    public Cliente(RequestCliente data) {
    }
}
