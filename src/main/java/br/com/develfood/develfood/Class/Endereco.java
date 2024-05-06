package br.com.develfood.develfood.Class;

import br.com.develfood.develfood.Record.AddressDTO;
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
    private String cep;
    private String state;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    public Endereco(AddressDTO body) {
        this.rua = body.rua();
        this.numero = body.numero();
        this.cidade = body.cidade();
        this.bairro = body.bairro();
        this.cep = body.cep();
        this.state = body.state();
    }
}
