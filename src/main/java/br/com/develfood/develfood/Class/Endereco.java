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
    private String street;
    private String number;
    private String city;
    private String neigbourhood;
    private String cep;
    private String state;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    public Endereco(AddressDTO body) {
        this.street = body.rua();
        this.number = String.valueOf(body.numero());
        this.city = body.cidade();
        this.neigbourhood = body.bairro();
        this.cep = body.cep();
        this.state = body.state();
    }
}
